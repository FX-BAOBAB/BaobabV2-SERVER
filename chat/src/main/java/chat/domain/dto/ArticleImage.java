package chat.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ArticleImage {

    private String imageId;

    private String imageUrl;

    public static ArticleImage of(String imageId, String imageUrl) {
        return ArticleImage.builder()
            .imageId(imageId)
            .imageUrl(imageUrl)
            .build();
    }

}
