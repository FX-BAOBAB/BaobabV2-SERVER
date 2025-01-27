package article.core.common.converter;

import article.adapter.input.web.request.ArticleUpdateRequest;
import article.adapter.output.persistence.repository.Article;
import article.domain.command.ArticleUpdateCommand;
import global.annotation.Converter;

@Converter
public class ArticleConverter {

    public ArticleUpdateCommand toUpdateCommand(ArticleUpdateRequest articleUpdateRequest, String userId) {
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

    public Article toArticle(ArticleUpdateCommand articleUpdateCommand) {
        return Article.builder().id(articleUpdateCommand.getId()).title(articleUpdateCommand.getTitle()).content(articleUpdateCommand.getContent()).category(articleUpdateCommand.getCategory()).price(articleUpdateCommand.getPrice()).registeredAt(articleUpdateCommand.getRegisteredAt()).status(articleUpdateCommand.getStatus()).userId(articleUpdateCommand.getUserId()).imageList(articleUpdateCommand.getImageList()).build();
    }
}
