package article.domain.command;

import article.adapter.input.web.request.ArticleSaleStatusUpdateRequest;
import article.adapter.output.persistence.enums.ArticleSaleStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class ArticleSaleStatusUpdateCommand {

    private String articleId;

    private String userId;

    private ArticleSaleStatus status;

    public static ArticleSaleStatusUpdateCommand of(ArticleSaleStatusUpdateRequest request, String userId) {
        return ArticleSaleStatusUpdateCommand.builder()
            .articleId(request.getArticleId())
            .userId(userId)
            .status(request.getStatus())
            .build();
    }

}
