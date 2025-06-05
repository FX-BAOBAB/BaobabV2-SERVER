package article.core.common.error.report;

import global.errorcode.ErrorCodeIfs;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ArticleReportErrorCode implements ErrorCodeIfs {

    SELF_ARTICLE_REPORT_NOT_ALLOWED(HttpStatus.BAD_REQUEST.value(), 1600, "자신의 게시글은 신고할 수 없습니다."),
    DUPLICATE_ARTICLE_REPORT(HttpStatus.CONFLICT.value(), 1601, "이미 해당 게시글을 신고하였습니다.")
    ;

    private final Integer httpCode;
    private final Integer errorCode;
    private final String description;

}