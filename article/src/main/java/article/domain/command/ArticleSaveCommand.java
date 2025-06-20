package article.domain.command;

import article.adapter.input.web.request.ArticleSaveRequest;
import article.adapter.output.persistence.enums.ArticleCategory;
import article.adapter.output.persistence.enums.ArticleSaleStatus;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Builder
public class ArticleSaveCommand {

    private String title;

    private String content;

    private ArticleCategory category;

    private int price;

    private LocalDateTime registeredAt;

    private ArticleSaleStatus status;

    private String userId;

    private List<MultipartFile> imageList;

    public static ArticleSaveCommand of(
        ArticleSaveRequest request,
        List<MultipartFile> imageList,
        String userId
    ) {
        return ArticleSaveCommand.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .category(request.getCategory())
                .price(request.getPrice())
                .imageList(imageList)
                .status(ArticleSaleStatus.ON_SALE)
                .registeredAt(LocalDateTime.now())
                .userId(userId)
                .build();
    }

}
