package article.domain.dto;

import article.adapter.output.persistence.enums.ArticleCategory;
import article.adapter.output.persistence.enums.ArticleStatus;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ArticleInfo {

    private String id;

    private String title;

    private String content;

    private ArticleCategory category;

    private int price;

    private LocalDateTime registeredAt;

    private ArticleStatus status;

    private String nickName;

    private List<ArticleImage> imageList;

}
