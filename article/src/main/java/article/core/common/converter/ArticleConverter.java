package article.core.common.converter;

import article.adapter.input.web.request.ArticleSaveRequest;
import article.adapter.output.persistence.repository.Article;
import article.domain.command.ArticleCommand;
import article.domain.command.ArticleSaveCommand;
import file.domain.ImageMetaData;
import global.annotation.Converter;
import java.util.List;

@Converter
public class ArticleConverter {

    public ArticleSaveCommand toSaveCommand(ArticleSaveRequest articleSaveRequest) {
        return ArticleSaveCommand.builder()
            .title(articleSaveRequest.getTitle())
            .content(articleSaveRequest.getContent())
            .category(articleSaveRequest.getCategory())
            .price(articleSaveRequest.getPrice())
            .imageList(articleSaveRequest.getImageList())
            .build();
    }

    public ArticleCommand toArticleCommand(ArticleSaveCommand articleSaveCommand, List<ImageMetaData> imageMetaDataList) {
        return ArticleCommand.builder()
            .title(articleSaveCommand.getTitle())
            .content(articleSaveCommand.getContent())
            .category(articleSaveCommand.getCategory())
            .price(articleSaveCommand.getPrice())
            .imageList(imageMetaDataList)
            .build();
    }

    public Article toArticle(ArticleCommand articleCommand) {
        return Article.builder()
            .title(articleCommand.getTitle())
            .content(articleCommand.getContent())
            .category(articleCommand.getCategory())
            .price(articleCommand.getPrice())
            .imageList(articleCommand.getImageList())
            .build();
    }
}
