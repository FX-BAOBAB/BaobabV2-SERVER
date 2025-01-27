package article.application;

import article.adapter.output.persistence.repository.Article;
import article.application.port.input.DefaultArticleUseCase;
import article.application.port.output.ArticlePersistencePort;
import article.core.common.error.article.ArticleErrorCode;
import article.core.common.exception.article.ArticleNotFoundException;
import article.core.common.exception.article.NotPermittedException;
import article.domain.command.ArticleSaveCommand;
import article.domain.command.ArticleSearchCommand;
import article.domain.command.ArticleUpdateCommand;
import article.domain.dto.ArticleImage;
import article.domain.dto.ArticleSaveForm;
import file.application.port.input.ImageMetaDataUseCase;
import file.application.port.input.ImageStorageUseCase;
import file.core.common.error.ImageErrorCode;
import file.core.common.exception.image.ImageStorageException;
import file.domain.ImageCommand;
import file.domain.ImageKind;
import file.domain.ImageMetaData;
import global.utils.ImageIdUtils;
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

    private final ImageMetaDataUseCase imageMetaDataUseCase;

    private final ArticlePersistencePort articlePersistencePort;

    private final String IMAGE_MODULE_CODE = "ARTICLE";

    @Override
    public boolean saveArticle(ArticleSaveCommand articleSaveCommand) {
        List<ImageMetaData> imageMetaDataList = imageMetaDataUseCase.processImageMetaDataList(
            IMAGE_MODULE_CODE, articleSaveCommand.getUserId(), articleSaveCommand.getImageList());

        ArticleSaveForm form = ArticleSaveForm.of(articleSaveCommand, imageMetaDataList);

        return articlePersistencePort.saveArticle(form);
    }

    @Override
    public List<Article> getArticleList(ArticleSearchCommand articleSearchCommand) {
        return articlePersistencePort.getArticleList(articleSearchCommand);
    }

    @Override
    public boolean updateArticle(ArticleUpdateCommand articleUpdateCommand) {

        Article article = articlePersistencePort.getArticleById(articleUpdateCommand.getId())
            .orElseThrow(() -> new ArticleNotFoundException(ArticleErrorCode.ARTICLE_NOT_FOUND));

        String userId = articleUpdateCommand.getUserId();

          if (!userId.equals(article.getUserId())) {
              throw new NotPermittedException(ArticleErrorCode.NOT_PERMITTED);
          }

        Optional.ofNullable(articleUpdateCommand.getDeleteImageIdList())
            .filter(list -> !list.isEmpty())
            .ifPresent(imageStorageUseCase::deleteImageList);

        if (articleUpdateCommand.getUpdateImages() != null) {
            try {
                List<ImageCommand> imageCommandList = articleUpdateCommand.getUpdateImages().stream()
                    .map(image -> {
                        String imageId = imageIdUtils.generateImageId(IMAGE_MODULE_CODE, userId);

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
                throw new ImageStorageException(ImageErrorCode.IMAGE_UPLOAD_ERROR);
            }
        }
        articleUpdateCommand.setRegisteredAt(article.getRegisteredAt());

        articleUpdateCommand.setUserId(article.getUserId());

        return articlePersistencePort.updateArticle(articleUpdateCommand);
    }

    @Override
    public boolean deleteArticle(String articleId, String userId) {

        Article article = articlePersistencePort.getArticleById(articleId)
            .orElseThrow(() -> new ArticleNotFoundException(ArticleErrorCode.ARTICLE_NOT_FOUND));

        if (!userId.equals(article.getUserId())) {
            throw new NotPermittedException(ArticleErrorCode.NOT_PERMITTED);
        }

        article.getImageList().forEach(image -> imageStorageUseCase.deleteImage(image.getImageId()));

        return articlePersistencePort.deleteArticle(articleId);
    }

}
