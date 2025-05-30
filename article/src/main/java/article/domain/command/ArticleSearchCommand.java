package article.domain.command;

import article.adapter.input.web.request.ArticleSearchCondition;
import article.adapter.output.persistence.enums.ArticleCategory;
import article.adapter.output.persistence.enums.ArticleSaleStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Pageable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ArticleSearchCommand {

    private String title;

    private String content;

    private ArticleCategory category;

    private Pageable pageable;

    private String userId;

    private String articleId;

    private ArticleSaleStatus status;

    // static factory Method
    public static ArticleSearchCommand of(ArticleSearchCondition condition){
        return ArticleSearchCommand.builder()
                .title(condition.getTitle())
                .content(condition.getContent())
                .category(condition.getCategory())
                .pageable(condition.getPageable())
                .status(condition.getStatus())
                .userId(condition.getUserId())
                .articleId(condition.getArticleId())
                .build();
    }

}
