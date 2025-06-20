package article.domain.command;

import article.adapter.input.web.request.ArticleUpdateRequest;
import article.adapter.output.persistence.enums.ArticleCategory;
import article.adapter.output.persistence.enums.ArticleSaleStatus;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Builder
public class ArticleUpdateCommand {

    private String articleId;

    private String title;

    private String content;

    private ArticleCategory category;

    private int price;

    private ArticleSaleStatus status;

    private List<MultipartFile> addImages;

    private List<String> deleteImageIdList;

    private LocalDateTime registeredAt;

    private String userId;

    public static ArticleUpdateCommand of(
        ArticleUpdateRequest articleUpdateRequest,
        List<MultipartFile> addImages,
        String articleId,
        String userId
    ) {
        return ArticleUpdateCommand.builder()
            .articleId(articleId)
            .title(articleUpdateRequest.getTitle())
            .content(articleUpdateRequest.getContent())
            .category(articleUpdateRequest.getCategory())
            .price(articleUpdateRequest.getPrice())
            .addImages(addImages)
            .deleteImageIdList(articleUpdateRequest.getDeleteImageIdList())
            .userId(userId)
            .build();
    }

}
