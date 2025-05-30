package article.domain.command;

import article.adapter.input.web.request.ArticleUpdateRequest;
import article.adapter.output.persistence.enums.ArticleCategory;
import article.adapter.output.persistence.enums.ArticleSaleStatus;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ArticleUpdateCommand {

    private String id;

    private String title;

    private String content;

    private ArticleCategory category;

    private int price;

    private ArticleSaleStatus status;

    private List<MultipartFile> addImages;

    private List<String> deleteImageIdList;

    private LocalDateTime registeredAt;

    private String userId;

    public static ArticleUpdateCommand of(ArticleUpdateRequest articleUpdateRequest,
        List<MultipartFile> addImages, String userId) {
        return ArticleUpdateCommand.builder()
            .id(articleUpdateRequest.getId())
            .title(articleUpdateRequest.getTitle())
            .content(articleUpdateRequest.getContent())
            .category(articleUpdateRequest.getCategory())
            .price(articleUpdateRequest.getPrice())
            .addImages(addImages)
            .deleteImageIdList(articleUpdateRequest.getDeleteImageIdList())
            .status(articleUpdateRequest.getStatus())
            .userId(userId)
            .build();
    }

}
