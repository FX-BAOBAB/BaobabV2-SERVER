package article.domain.dto;

import article.adapter.output.persistence.enums.ArticleCategory;
import article.adapter.output.persistence.enums.ArticleSaleStatus;
import article.adapter.output.persistence.enums.ArticleVisibilityStatus;
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

    private ArticleSaleStatus status;

    private String userId;

    private List<ArticleImage> imageList;

    private Long viewCount;

    private ArticleVisibilityStatus visibilityStatus;

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
            .viewCount(article.getViewCount())
            .visibilityStatus(article.getVisibilityStatus())
            .build();
    }

    public static List<ArticleUpdateForm> of(List<Article> articleList) {
        return articleList.stream()
            .map(article -> ArticleUpdateForm.builder()
                .id(article.getId())
                .title(article.getTitle())
                .content(article.getContent())
                .category(article.getCategory())
                .price(article.getPrice())
                .registeredAt(article.getRegisteredAt())
                .status(article.getSaleStatus())
                .userId(article.getUserId())
                .imageList(article.getImageList())
                .viewCount(article.getViewCount())
                .visibilityStatus(article.getVisibilityStatus())
                .build())
            .toList();
    }

    public static ArticleUpdateForm of(Article article) {
        return ArticleUpdateForm.builder()
            .id(article.getId())
            .title(article.getTitle())
            .content(article.getContent())
            .category(article.getCategory())
            .price(article.getPrice())
            .registeredAt(article.getRegisteredAt())
            .status(article.getSaleStatus())
            .userId(article.getUserId())
            .imageList(article.getImageList())
            .viewCount(article.getViewCount())
            .visibilityStatus(article.getVisibilityStatus())
            .build();
    }

}
