package article.adapter.input.web.request;

import article.adapter.output.persistence.enums.ArticleCategory;
import article.adapter.output.persistence.enums.ArticleSaleStatus;
import lombok.Data;
import org.springframework.data.domain.Pageable;

@Data
public class ArticleSearchCondition {

    private String title;

    private String content;

    private ArticleCategory category;

    private Pageable pageable;

    private String userId;

    private String articleId;

    private ArticleSaleStatus status;

}
