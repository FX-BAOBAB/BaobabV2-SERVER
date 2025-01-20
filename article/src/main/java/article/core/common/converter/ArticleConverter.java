package article.core.common.converter;

import article.adapter.input.web.request.ArticleSaveRequest;
import article.adapter.input.web.request.ArticleSearchCondition;
import article.adapter.input.web.request.ArticleUpdateRequest;
import article.adapter.input.web.response.ArticleListResponse;
import article.adapter.output.persistence.repository.Article;
import article.domain.command.ArticleSaveCommand;
import article.domain.command.ArticleSearchCommand;
import article.domain.command.ArticleUpdateCommand;
import article.domain.dto.ArticleImage;
import article.domain.dto.ArticleInfo;
import article.domain.dto.ArticleSaveForm;
import file.domain.ImageMetaData;
import global.annotation.Converter;
import java.util.List;
import java.util.stream.Collectors;

@Converter
public class ArticleConverter {

    public ArticleSaveCommand toSaveCommand(ArticleSaveRequest articleSaveRequest, String userId) {
        return ArticleSaveCommand.builder()
            .title(articleSaveRequest.getTitle())
            .content(articleSaveRequest.getContent())
            .category(articleSaveRequest.getCategory())
            .price(articleSaveRequest.getPrice())
            .imageList(articleSaveRequest.getImageList())
            .userId(userId)
            .build();
    }

    public ArticleSearchCommand toSearchCommand(ArticleSearchCondition articleSearchCondition) {
        return ArticleSearchCommand.builder()
            .title(articleSearchCondition.getTitle())
            .content(articleSearchCondition.getContent())
            .category(articleSearchCondition.getCategory())
            .pageable(articleSearchCondition.getPageable())
            .build();
    }

    public ArticleUpdateCommand toUpdateCommand(ArticleUpdateRequest articleUpdateRequest,
        String userId) {
        return ArticleUpdateCommand.builder()
            .id(articleUpdateRequest.getId())
            .title(articleUpdateRequest.getTitle())
            .content(articleUpdateRequest.getContent())
            .category(articleUpdateRequest.getCategory())
            .status(articleUpdateRequest.getStatus())
            .price(articleUpdateRequest.getPrice())
            .imageList(articleUpdateRequest.getImageList())
            .updateImages(articleUpdateRequest.getUpdateImages())
            .deleteImageIdList(articleUpdateRequest.getDeleteImageIdList())
            .userId(userId)
            .build();
    }

    public ArticleSaveForm toArticleSaveForm(ArticleSaveCommand articleSaveCommand,
        List<ImageMetaData> imageMetaDataList) {
        return ArticleSaveForm.builder()
            .title(articleSaveCommand.getTitle())
            .content(articleSaveCommand.getContent())
            .category(articleSaveCommand.getCategory())
            .price(articleSaveCommand.getPrice())
            .registeredAt(articleSaveCommand.getRegisteredAt())
            .status(articleSaveCommand.getStatus())
            .userId(articleSaveCommand.getUserId())
            .imageList(imageMetaDataList.stream()
                .map(image -> ArticleImage.builder()
                    .imageId(image.getId())
                    .imageUrl(image.getUrl())
                    .build())
                .toList())
            .build();
    }

    public Article toArticle(ArticleSaveForm articleSaveForm) {
        return Article.builder()
            .title(articleSaveForm.getTitle())
            .content(articleSaveForm.getContent())
            .category(articleSaveForm.getCategory())
            .price(articleSaveForm.getPrice())
            .registeredAt(articleSaveForm.getRegisteredAt())
            .status(articleSaveForm.getStatus())
            .userId(articleSaveForm.getUserId())
            .imageList(articleSaveForm.getImageList())
            .build();
    }

    public Article toArticle(ArticleUpdateCommand articleUpdateCommand) {
        return Article.builder()
            .id(articleUpdateCommand.getId())
            .title(articleUpdateCommand.getTitle())
            .content(articleUpdateCommand.getContent())
            .category(articleUpdateCommand.getCategory())
            .price(articleUpdateCommand.getPrice())
            .registeredAt(articleUpdateCommand.getRegisteredAt())
            .status(articleUpdateCommand.getStatus())
            .userId(articleUpdateCommand.getUserId())
            .imageList(articleUpdateCommand.getImageList())
            .build();
    }

    public ArticleListResponse toResponse(List<Article> articleList) {
        return ArticleListResponse.builder()
            .articles(articleList.stream()
                .map(article -> ArticleInfo.builder()
                    .id(article.getId())
                    .title(article.getTitle())
                    .content(article.getContent())
                    .category(article.getCategory())
                    .price(article.getPrice())
                    .registeredAt(article.getRegisteredAt())
                    .status(article.getStatus())
//                    .nickName(article.getUserId())
                    .imageList(article.getImageList())
                    .build())
                .collect(Collectors.toList()))
            .build();
    }
}
