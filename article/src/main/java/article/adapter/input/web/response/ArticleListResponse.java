package article.adapter.input.web.response;

import article.domain.dto.ArticleInfo;
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

    private List<ArticleInfo> articles;

}
