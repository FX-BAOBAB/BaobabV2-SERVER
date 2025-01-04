package article.domain.command;

import article.adapter.output.persistence.enums.ArticleCategory;
import article.adapter.output.persistence.enums.ArticleStatus;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ArticleUpdateCommand {

    private String title;

    private String content;

    private ArticleCategory category;

    private int price;

    private ArticleStatus status;

    private List<String> imageIdList;

}
