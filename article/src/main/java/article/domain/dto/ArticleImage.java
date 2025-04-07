package article.domain.dto;

import file.domain.ImageKind;
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

    private ImageKind imageKind;

    public static ArticleImage of(String imageId, String imageUrl, ImageKind imageKind) {
        return ArticleImage.builder()
            .imageId(imageId)
            .imageUrl(imageUrl)
            .imageKind(imageKind)
            .build();
    }

}
