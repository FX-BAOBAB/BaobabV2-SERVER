package article.adapter.input.web.response;

import article.adapter.output.client.dto.UserSimpleInfo;
import article.adapter.output.persistence.enums.ArticleCategory;
import article.adapter.output.persistence.enums.ArticleStatus;
import article.adapter.output.persistence.repository.Article;
import article.domain.dto.ArticleImage;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ArticleInfoResponse {

    private String id;

    private String title;

    private String content;

    private ArticleCategory category;

    private int price;

    private LocalDateTime registeredAt;

    private ArticleStatus status;

    private List<ArticleImage> imageList;

    private String nickname;

    private String profileImageUrl;

    private Boolean isMine;

    public static ArticleInfoResponse of(
        Article article,
        UserSimpleInfo userSimpleInfo,
        Boolean isMine
    ) {
        return ArticleInfoResponse.builder()
            .id(article.getId())
            .title(article.getTitle())
            .content(article.getContent())
            .category(article.getCategory())
            .price(article.getPrice())
            .registeredAt(article.getRegisteredAt())
            .status(article.getStatus())
            .nickname(userSimpleInfo.getNickname())
            .profileImageUrl(userSimpleInfo.getProfileImageUrl())
            .imageList(article.getImageList())
            .isMine(isMine)
            .build();
    }

}
