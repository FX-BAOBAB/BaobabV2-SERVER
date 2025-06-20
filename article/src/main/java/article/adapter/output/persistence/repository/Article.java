package article.adapter.output.persistence.repository;

import article.adapter.output.persistence.enums.ArticleCategory;
import article.adapter.output.persistence.enums.ArticleSaleStatus;
import article.adapter.output.persistence.enums.ArticleVisibilityStatus;
import article.domain.dto.ArticleImage;
import article.domain.dto.ArticleSaveForm;
import article.domain.dto.ArticleUpdateForm;
import java.time.LocalDateTime;
import java.util.List;
import com.querydsl.core.annotations.QueryEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Builder
@QueryEntity
@Document(collection = "article")
public class Article {

    @Id
    private String id;

    private String title;

    private String content;

    private ArticleCategory category;

    private int price;

    private LocalDateTime registeredAt;

    private ArticleSaleStatus saleStatus;

    private String userId;

    private List<ArticleImage> imageList;

    @Builder.Default
    private List<String> bookmarkUserIdList = List.of();

    @Builder.Default
    private Long viewCount = 0L;

    @Builder.Default
    private Long reportCount = 0L;

    @Builder.Default
    private ArticleVisibilityStatus visibilityStatus = ArticleVisibilityStatus.VISIBILITY;

    public static Article of(ArticleSaveForm form) {
        return Article.builder()
            .title(form.getTitle())
            .content(form.getContent())
            .category(form.getCategory())
            .price(form.getPrice())
            .registeredAt(form.getRegisteredAt())
            .saleStatus(form.getStatus())
            .userId(form.getUserId())
            .imageList(form.getImageList())
            .build();
    }

    public static Article of(ArticleUpdateForm form) {
        return Article.builder()
            .id(form.getArticleId())
            .title(form.getTitle())
            .content(form.getContent())
            .category(form.getCategory())
            .price(form.getPrice())
            .registeredAt(form.getRegisteredAt())
            .saleStatus(form.getStatus())
            .userId(form.getUserId())
            .imageList(form.getImageList())
            .viewCount(form.getViewCount())
            .reportCount(form.getReportCount())
            .visibilityStatus(form.getVisibilityStatus())
            .bookmarkUserIdList(form.getBookmarkUserIdList())
            .build();
    }

    public static List<Article> of(List<ArticleUpdateForm> forms) {
        return forms.stream()
            .map(form -> Article.builder()
                .id(form.getArticleId())
                .title(form.getTitle())
                .content(form.getContent())
                .category(form.getCategory())
                .price(form.getPrice())
                .registeredAt(form.getRegisteredAt())
                .saleStatus(form.getStatus())
                .userId(form.getUserId())
                .imageList(form.getImageList())
                .viewCount(form.getViewCount())
                .visibilityStatus(form.getVisibilityStatus())
                .bookmarkUserIdList(form.getBookmarkUserIdList())
                .build()
            ).toList();
    }

}
