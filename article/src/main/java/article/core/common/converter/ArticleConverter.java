package article.core.common.converter;

import article.adapter.input.web.request.ArticleSaveRequest;
import article.adapter.output.persistence.repository.Article;
import article.domain.command.ArticleCommand;
import article.domain.command.ArticleSaveCommand;
import article.domain.dto.ArticleImage;
import file.domain.ImageMetaData;
import global.annotation.Converter;
import java.util.List;
import java.util.stream.Collectors;

@Converter
public class ArticleConverter {

    public ArticleSaveCommand toSaveCommand(ArticleSaveRequest articleSaveRequest,
        List<ImageMetaData> imageMetaDataList, String userId) {

        List<ArticleImage> imageInfoList = imageMetaDataList.stream()
            .map(imageMetaData -> ArticleImage.builder()
                .imageId(imageMetaData.getId())
                .imageUrl(imageMetaData.getUrl())
                .build())
            .toList();

        return ArticleSaveCommand.builder()
            .title(articleSaveRequest.getTitle())
            .content(articleSaveRequest.getContent())
            .category(articleSaveRequest.getCategory())
            .price(articleSaveRequest.getPrice())
            .userId(userId)
            .imageList(imageInfoList)
            .build();
    }

    public Article toArticle(ArticleSaveCommand articleSaveCommand) {
        return Article.builder()
            .title(articleSaveCommand.getTitle())
            .content(articleSaveCommand.getContent())
            .category(articleSaveCommand.getCategory())
            .price(articleSaveCommand.getPrice())
            .registeredAt(articleSaveCommand.getRegisteredAt())
            .status(articleSaveCommand.getStatus())
            .userId(articleSaveCommand.getUserId())
            .imageList(articleSaveCommand.getImageList())
            .build();
    }
}
