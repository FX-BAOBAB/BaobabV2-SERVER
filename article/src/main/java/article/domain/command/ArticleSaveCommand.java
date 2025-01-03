package article.domain.command;

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
public class ArticleSaveCommand {

    private String title;

    private String content;

    private String category;

    private int price;

    private List<Long> imageIdList;

    private LocalDateTime registeredAt;

    private String status;

    private Long userId;

}
