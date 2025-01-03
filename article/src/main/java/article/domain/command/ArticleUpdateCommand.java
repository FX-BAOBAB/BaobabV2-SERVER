package article.domain.command;

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

    private String category;

    private int price;

    private String status;

    private List<Long> imageIdList;

}
