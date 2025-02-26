package chat.adapter.output.client.response;

import chat.domain.dto.ArticleImage;
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

}
