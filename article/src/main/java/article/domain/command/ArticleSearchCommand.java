package article.domain.command;

import article.adapter.output.persistence.enums.ArticleCategory;
import java.util.List;
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

    private String userId;

    private String articleId;

    private String title;

    private String content;

    private ArticleCategory category;

    private Pageable pageable;
}
