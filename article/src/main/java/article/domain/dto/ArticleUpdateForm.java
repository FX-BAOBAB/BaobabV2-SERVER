package article.domain.dto;

import article.adapter.output.persistence.enums.ArticleCategory;
import article.adapter.output.persistence.enums.ArticleStatus;
import article.adapter.output.persistence.repository.Article;
import article.domain.command.ArticleUpdateCommand;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ArticleUpdateForm {

    private String id;

    private String title;

    private String content;

    private ArticleCategory category;

    private int price;

    private LocalDateTime registeredAt;

    private ArticleStatus status;

    private String userId;

    private List<ArticleImage> imageList;

    public static ArticleUpdateForm of(ArticleUpdateCommand command, Article article) {
        return ArticleUpdateForm.builder()
            .id(command.getId())
            .title(command.getTitle())
            .content(command.getContent())
            .category(command.getCategory())
            .price(command.getPrice())
            .registeredAt(article.getRegisteredAt())
            .status(command.getStatus())
            .userId(article.getUserId())
            .imageList(article.getImageList())
            .build();
    }

}
