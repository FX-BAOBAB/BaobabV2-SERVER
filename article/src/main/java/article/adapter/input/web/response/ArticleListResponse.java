package article.adapter.input.web.response;

import article.adapter.output.persistence.repository.Article;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ArticleListResponse {

    private List<Article> articles;

}
