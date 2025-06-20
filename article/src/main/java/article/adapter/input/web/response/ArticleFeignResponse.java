package article.adapter.input.web.response;

import article.domain.dto.ArticleImage;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ArticleFeignResponse {

    private String userId;

    private ArticleImage articleImage;

    public static ArticleFeignResponse of(String userId, ArticleImage imageList) {
        return ArticleFeignResponse.builder()
            .userId(userId)
            .articleImage(imageList)
            .build();
    }

}
