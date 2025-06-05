package article.core.common.exception;

import article.core.common.error.report.ArticleReportErrorCode;
import article.core.common.exception.report.ArticleReportDuplicationException;
import article.core.common.exception.report.SelfArticleReportNotAllowedException;
import global.api.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ArticleReportExceptionHandler {

    @ExceptionHandler(value = SelfArticleReportNotAllowedException.class)
    public ResponseEntity<Api<Object>> notAllowedSelfArticleReportException(
        SelfArticleReportNotAllowedException e) {
        log.error("", e);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(Api.ERROR(ArticleReportErrorCode.SELF_ARTICLE_REPORT_NOT_ALLOWED));
    }

    @ExceptionHandler(value = ArticleReportDuplicationException.class)
    public ResponseEntity<Api<Object>> duplicateArticleReportException(
        ArticleReportDuplicationException e) {
        log.error("", e);
        return ResponseEntity.status(HttpStatus.CONFLICT)
            .body(Api.ERROR(ArticleReportErrorCode.DUPLICATE_ARTICLE_REPORT));
    }

}
