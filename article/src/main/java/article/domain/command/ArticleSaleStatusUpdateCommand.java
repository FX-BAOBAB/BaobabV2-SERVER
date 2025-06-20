package article.domain.command;

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

    public static ArticleSaleStatusUpdateCommand of(
        ArticleSaleStatus status,
        String articleId,
        String userId
    ) {
        return ArticleSaleStatusUpdateCommand.builder()
            .articleId(articleId)
            .userId(userId)
            .status(status)
            .build();
    }

}
