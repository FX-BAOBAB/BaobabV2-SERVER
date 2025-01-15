package article.application;

import article.adapter.output.persistence.enums.ArticleStatus;
import article.adapter.output.persistence.repository.Article;
import article.application.port.input.DefaultArticleUseCase;
import article.application.port.output.ArticlePersistencePort;
import article.core.common.error.ArticleErrorCode;
import article.core.common.exception.article.ArticleNotFoundException;
import article.core.common.exception.article.NotPermittedException;
import article.domain.command.ArticleSaveCommand;
import article.domain.command.ArticleSearchCommand;
import article.domain.command.ArticleUpdateCommand;
import article.domain.dto.ArticleImage;
import file.application.port.input.ImageStorageUseCase;
import file.core.common.error.ImageErrorCode;
import file.core.common.exception.image.ImageStorageException;
import file.domain.ImageCommand;
import file.domain.ImageKind;
import global.utils.ImageIdUtils;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ArticleService implements DefaultArticleUseCase {

    private final ImageIdUtils imageIdUtils;

    private final ImageStorageUseCase imageStorageUseCase;

    private final ArticlePersistencePort articlePersistencePort;

    @Override
    public boolean saveArticle(ArticleSaveCommand articleSaveCommand) {

        articleSaveCommand.setStatus(ArticleStatus.ON_SALE);

        articleSaveCommand.setRegisteredAt(LocalDateTime.now());

        return articlePersistencePort.saveArticle(articleSaveCommand);
    }

    @Override
    public List<Article> getMyArticles(ArticleSearchCommand command) {
        return articlePersistencePort.getMyArticles(command.getUserId(), command.getPageable());
    }

    @Override
    public Article getArticlesBy(String articleId) {
        return articlePersistencePort.findById(articleId).orElseThrow(() ->
            new ArticleNotFoundException(ArticleErrorCode.ARTICLE_NOT_FOUND));
    }

    @Override
    public List<Article> getArticleList(ArticleSearchCommand articleSearchCommand) {
        return articlePersistencePort.getArticleList(articleSearchCommand);
    }

    @Override
    public boolean updateArticle(ArticleUpdateCommand articleUpdateCommand) {

        Article article = articlePersistencePort.findById(articleUpdateCommand.getId())
            .orElseThrow(() -> new ArticleNotFoundException(ArticleErrorCode.ARTICLE_NOT_FOUND));

          // TODO User 처리 후 본인 게시물인지 확인
          /*if (!"userId".equals(article.getUserId())) {
              throw new NotPermittedException(ArticleErrorCode.NOT_PERMITTED);
          }*/

        Optional.ofNullable(articleUpdateCommand.getDeleteImageIdList())
            .filter(list -> !list.isEmpty())
            .ifPresent(imageStorageUseCase::deleteImageList);

        try {
            List<ImageCommand> imageCommandList = articleUpdateCommand.getUpdateImages().stream()
                .map(image -> {
                    // TODO 유저 아이디 처리
                    // TODO Module Code Environment DB 처리
                    String imageId = imageIdUtils.generateImageId("ART", "Test");

                    return ImageCommand.builder()
                        .id(imageId)
                        .file(image)
                        .kind(ImageKind.ARTICLE)
                        .build();
                }).toList();

            List<ArticleImage> updateImageList = imageStorageUseCase.saveImageList(imageCommandList)
                .get().stream().map(imageMetaData -> ArticleImage.builder()
                    .imageId(imageMetaData.getId())
                    .imageUrl(imageMetaData.getUrl())
                    .build()).toList();

            Optional.ofNullable(articleUpdateCommand.getImageList())
                .ifPresentOrElse(
                    imageList -> imageList.addAll(updateImageList),
                    () -> articleUpdateCommand.setImageList(updateImageList)
                );

        } catch (InterruptedException | ExecutionException e) {
            // TODO Mongo DB Exception 놓칠 위험있음 Catch 부 변경 필요
            throw new ImageStorageException(ImageErrorCode.IMAGE_STORAGE_ERROR);
        }

        articleUpdateCommand.setRegisteredAt(article.getRegisteredAt());

        articleUpdateCommand.setUserId(article.getUserId());

        return articlePersistencePort.updateArticle(articleUpdateCommand);
    }

    @Override
    public boolean deleteArticle(String articleId) {
        return false;
    }

}
