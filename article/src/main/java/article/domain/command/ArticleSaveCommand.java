package article.domain.command;

import article.adapter.input.web.request.ArticleSaveRequest;
import article.adapter.output.persistence.enums.ArticleCategory;
import article.adapter.output.persistence.enums.ArticleStatus;
import java.time.LocalDateTime;
import java.util.List;

import article.adapter.output.persistence.repository.Article;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ArticleSaveCommand {

    private String title;

    private String content;

    private ArticleCategory category;

    private int price;

    private LocalDateTime registeredAt;

    private ArticleStatus status;

    private String userId;

    private List<MultipartFile> imageList;

    public static ArticleSaveCommand of(ArticleSaveRequest request,String userId) {
        return ArticleSaveCommand.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .category(request.getCategory())
                .price(request.getPrice())
                .imageList(request.getImageList())
                .status(ArticleStatus.ON_SALE)
                .registeredAt(LocalDateTime.now())
                .userId(userId)
                .build();
    }

}
