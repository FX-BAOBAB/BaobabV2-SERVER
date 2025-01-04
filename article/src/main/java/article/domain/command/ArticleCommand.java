package article.domain.command;

import article.adapter.output.persistence.enums.ArticleCategory;
import article.adapter.output.persistence.enums.ArticleStatus;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ArticleCommand {

    private String title;

    private String content;

    private ArticleCategory category;

    private int price;

    private List<Long> imageIdList;

    private LocalDateTime registeredAt;

    private ArticleStatus status;

    private String userId;

}
