package chat.adapter.output.client.dto;

import chat.domain.dto.ArticleImage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ArticleFeignInfo {

    private String userId;

    private ArticleImage articleImage;

}
