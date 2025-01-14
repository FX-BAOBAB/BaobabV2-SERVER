package article.adapter.input.web.request;

import article.adapter.output.persistence.enums.ArticleCategory;
import lombok.Data;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;

@Data
public class ArticleSearchCondition {

    // TODO Login User 대체 필요
    private String userId;

    private String title;

    private String content;

    private List<Long> articleIdList;

    private ArticleCategory category;

    private int page;

    private int size;

}
