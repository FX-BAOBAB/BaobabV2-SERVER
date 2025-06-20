package article.domain.dto;

import article.adapter.output.persistence.enums.ArticleCategory;
import article.adapter.output.persistence.enums.ArticleSaleStatus;
import article.domain.command.ArticleSaveCommand;
import file.domain.ImageMetaData;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ArticleSaveForm {

    private String title;

    private String content;

    private ArticleCategory category;

    private int price;

    private LocalDateTime registeredAt;

    private ArticleSaleStatus status;

    private String userId;

    private List<ArticleImage> imageList;

    public static ArticleSaveForm of(ArticleSaveCommand command, List<ImageMetaData> imageList) {
        return ArticleSaveForm.builder()
            .title(command.getTitle())
            .content(command.getContent())
            .category(command.getCategory())
            .price(command.getPrice())
            .registeredAt(command.getRegisteredAt())
            .status(command.getStatus())
            .userId(command.getUserId())
            .imageList(imageList.stream().map(imageMetaData ->
                    ArticleImage.of(imageMetaData.getId(), imageMetaData.getUrl(),
                        imageMetaData.getKind())).toList())
            .build();
    }

}
