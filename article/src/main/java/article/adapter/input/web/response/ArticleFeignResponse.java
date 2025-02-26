package article.adapter.input.web.response;

import article.domain.dto.ArticleImage;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ArticleFeignResponse {

    private String userId;

    private List<ArticleImage> imageList;

    public static ArticleFeignResponse of(String userId, List<ArticleImage> imageList) {
        return ArticleFeignResponse.builder()
            .userId(userId)
            .imageList(imageList)
            .build();
    }

}
