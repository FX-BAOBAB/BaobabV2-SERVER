package article.adapter.output.persistence.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ArticleVisibilityStatus {
    VISIBILITY("노출"),
    DELETED("삭제"),
    REPORTED("신고")
    ;

    private final String description;
}
