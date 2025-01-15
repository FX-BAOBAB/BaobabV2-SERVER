package article.domain.command;

import article.adapter.output.persistence.enums.ArticleCategory;
import article.adapter.output.persistence.enums.ArticleStatus;
import article.domain.dto.ArticleImage;
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

    private ArticleStatus status;

    private List<MultipartFile> updateImages;

    private List<String> deleteImageIdList;

    private List<ArticleImage> imageList;

    private LocalDateTime registeredAt;

    private String userId;


}
