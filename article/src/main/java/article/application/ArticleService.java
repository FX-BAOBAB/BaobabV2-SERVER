package article.application;

import article.adapter.input.web.response.ArticleFeignResponse;
import article.adapter.input.web.response.ArticleInfoResponse;
import article.adapter.output.client.UserClient;
import article.adapter.output.persistence.enums.ArticleVisibilityStatus;
import article.adapter.output.persistence.repository.Article;
import article.application.port.input.DefaultArticleUseCase;
import article.application.port.output.ArticlePersistencePort;
import article.core.common.error.article.ArticleErrorCode;
import article.core.common.exception.article.ArticleNotFoundException;
import article.core.common.exception.article.BookmarkNotFoundException;
import article.core.common.exception.article.NotPermittedException;
import article.domain.command.ArticleSaleStatusUpdateCommand;
import article.domain.command.ArticleSaveCommand;
import article.domain.command.ArticleSearchCommand;
import article.domain.command.ArticleUpdateCommand;
import article.domain.dto.ArticleImage;
import article.domain.dto.ArticleSaveForm;
import article.domain.dto.ArticleUpdateForm;
import file.application.port.input.ImageMetaDataUseCase;
import file.application.port.input.ImageStorageUseCase;
import file.domain.ImageKind;
import file.domain.ImageMetaData;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ArticleService implements DefaultArticleUseCase {

    private final ImageStorageUseCase imageStorageUseCase;

    private final ImageMetaDataUseCase imageMetaDataUseCase;

    private final ArticleViewService articleViewService;

    private final ArticlePersistencePort articlePersistencePort;

    private final UserClient userClient;

    @Override
    public boolean saveArticle(ArticleSaveCommand articleSaveCommand) {
        List<ImageMetaData> imageMetaDataList = imageMetaDataUseCase.processImageMetaDataList(
            ImageKind.ARTICLE, articleSaveCommand.getUserId(), articleSaveCommand.getImageList());

        ArticleSaveForm form = ArticleSaveForm.of(articleSaveCommand, imageMetaDataList);

        return articlePersistencePort.saveArticle(form);
    }

    @Override
    public List<ArticleInfoResponse> getArticleList(ArticleSearchCommand command, String userId) {
        if (command.getArticleId() != null) {
            articleViewService.increaseViewCount(command.getArticleId(), userId);
        }

        List<Article> articles = articlePersistencePort.getArticleList(command, ArticleVisibilityStatus.VISIBILITY);
        return articles.stream()
            .map(article -> {
                boolean isMine = userId != null && userId.equals(article.getUserId());
                return ArticleInfoResponse.of(article,
                    userClient.getUserSimpleInfo(article.getUserId()), isMine);
            })
            .toList();
    }

    @Override
    public boolean updateArticle(ArticleUpdateCommand articleUpdateCommand) {
        Article article = articlePersistencePort.getArticleById(articleUpdateCommand.getId(),
                ArticleVisibilityStatus.VISIBILITY)
            .orElseThrow(() -> new ArticleNotFoundException(ArticleErrorCode.ARTICLE_NOT_FOUND));

        if (!articleUpdateCommand.getUserId().equals(article.getUserId())) {
            throw new NotPermittedException(ArticleErrorCode.NOT_PERMITTED);
        }

        deleteArticleImages(articleUpdateCommand, article);
        addArticleImages(articleUpdateCommand, article);

        return articlePersistencePort.updateArticle(
            ArticleUpdateForm.of(articleUpdateCommand, article));
    }

    public boolean updateArticleSaleStatus(ArticleSaleStatusUpdateCommand command) {
        Article article = articlePersistencePort.getArticleById(command.getArticleId(),
                ArticleVisibilityStatus.VISIBILITY)
            .orElseThrow(() -> new ArticleNotFoundException(ArticleErrorCode.ARTICLE_NOT_FOUND));

        if (!command.getUserId().equals(article.getUserId())) {
            throw new NotPermittedException(ArticleErrorCode.NOT_PERMITTED);
        }

        article.setSaleStatus(command.getStatus());

        return articlePersistencePort.updateArticle(ArticleUpdateForm.of(article));
    }

    @Override
    public boolean softDeleteArticle(String articleId, String userId) {
        Article article = articlePersistencePort.getArticleById(articleId,
                ArticleVisibilityStatus.VISIBILITY)
            .orElseThrow(() -> new ArticleNotFoundException(ArticleErrorCode.ARTICLE_NOT_FOUND));

        if (!userId.equals(article.getUserId())) {
            throw new NotPermittedException(ArticleErrorCode.NOT_PERMITTED);
        }

        article.setVisibilityStatus(ArticleVisibilityStatus.DELETED);
        return articlePersistencePort.updateArticle(ArticleUpdateForm.of(article));
    }

    @Override
    public void hardDeleteArticle(String articleId, List<ArticleImage> articleImageList) {
        articleImageList.forEach(image -> imageStorageUseCase.deleteImage(image.getImageId()));
        articlePersistencePort.deleteArticle(articleId);
    }

    private void deleteArticleImages(ArticleUpdateCommand articleUpdateCommand, Article article) {
        // 삭제 요청 이미지 아이디 리스트가 존재하면 이미지 삭제
        Optional.ofNullable(articleUpdateCommand.getDeleteImageIdList())
            .filter(list -> !list.isEmpty())
            .ifPresent(deleteImageIdList -> {
                // 아티클의 이미지 리스트에 이미지 삭제
                article.getImageList().removeIf(image ->
                    deleteImageIdList.contains(image.getImageId()));
                // 이미지 삭제
                imageStorageUseCase.deleteImageList(deleteImageIdList);
            });
    }

    private void addArticleImages(ArticleUpdateCommand articleUpdateCommand, Article article) {
        // 추가 이미지 리스트가 존재하면 이미지 추가
        Optional.ofNullable(articleUpdateCommand.getAddImages())
            .filter(list -> !list.isEmpty())
            .ifPresent(addImages -> {

                // 추가 이미지 메타 데이터 리스트 가져오기
                List<ImageMetaData> imageMetaDataList = imageMetaDataUseCase.processImageMetaDataList(
                    ImageKind.ARTICLE, articleUpdateCommand.getUserId(), addImages);

                // 아티클 이미지 객체 리스트 생성
                List<ArticleImage> articleImageList = imageMetaDataList.stream()
                    .map(imageMetaData -> ArticleImage.builder()
                        .imageId(imageMetaData.getId())
                        .imageUrl(imageMetaData.getUrl())
                        .build())
                    .toList();

                // 아티클 이미지 리스트에 이미지 추가
                article.getImageList().addAll(articleImageList);
            });
    }

    @Override
    public ArticleFeignResponse getArticleBy(String articleId) {
        Article article = articlePersistencePort.getArticleById(articleId,
                ArticleVisibilityStatus.VISIBILITY)
            .orElseThrow(() -> new ArticleNotFoundException(ArticleErrorCode.ARTICLE_NOT_FOUND));
        return ArticleFeignResponse.of(article.getUserId(), article.getImageList().get(0));
    }

    @Override
    public boolean bookmarkArticle(String articleId, String userId) {
        Article article = articlePersistencePort.getArticleById(articleId,
                ArticleVisibilityStatus.VISIBILITY)
            .orElseThrow(() -> new ArticleNotFoundException(ArticleErrorCode.ARTICLE_NOT_FOUND));

        List<String> bookmarkUserIdList = article.getBookmarkUserIdList();
        if (bookmarkUserIdList.contains(userId)) {
            return true;
        }

        bookmarkUserIdList.add(userId);
        article.setBookmarkUserIdList(bookmarkUserIdList);
        return articlePersistencePort.updateArticle(ArticleUpdateForm.of(article));
    }

    @Override
    public boolean unbookmarkArticle(String articleId, String userId) {
        Article article = articlePersistencePort.getArticleById(articleId,
                ArticleVisibilityStatus.VISIBILITY)
            .orElseThrow(() -> new ArticleNotFoundException(ArticleErrorCode.ARTICLE_NOT_FOUND));

        if (!article.getBookmarkUserIdList().contains(userId)) {
            throw new BookmarkNotFoundException(ArticleErrorCode.BOOKMARK_NOT_FOUND);
        }

        List<String> bookmarkUserIdList = article.getBookmarkUserIdList();
        bookmarkUserIdList.remove(userId);
        article.setBookmarkUserIdList(bookmarkUserIdList);
        return articlePersistencePort.updateArticle(ArticleUpdateForm.of(article));
    }

}
