package article.domain.command;

import article.adapter.input.web.request.ArticleSearchCondition;
import article.adapter.output.persistence.enums.ArticleCategory;
import article.adapter.output.persistence.enums.ArticleSaleStatus;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Pageable;

@Getter
@Builder
public class ArticleSearchCommand {

    private String title;

    private String content;

    private ArticleCategory category;

    private Pageable pageable;

    private String userId;

    private String articleId;

    private ArticleSaleStatus status;

    public static ArticleSearchCommand of(ArticleSearchCondition condition, String userId) {
        return ArticleSearchCommand.builder()
                .title(condition.getTitle())
                .content(condition.getContent())
                .category(condition.getCategory())
                .pageable(condition.getPageable())
                .status(condition.getStatus())
                .userId(userId)
                .articleId(condition.getArticleId())
                .build();
    }

    public static ArticleSearchCommand of(String articleId, String userId) {
        return ArticleSearchCommand.builder()
                .articleId(articleId)
                .pageable(Pageable.unpaged())
                .userId(userId)
                .build();
    }

}
