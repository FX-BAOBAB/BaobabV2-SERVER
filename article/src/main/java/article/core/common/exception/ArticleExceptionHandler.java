package article.core.common.exception;

import article.core.common.error.ArticleErrorCode;
import article.core.common.exception.article.ArticleNotFoundException;
import global.api.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ArticleExceptionHandler {

    @ExceptionHandler(value = ArticleNotFoundException.class)
    public ResponseEntity<Api<Object>> notFoundArticleException(ArticleNotFoundException e) {
        log.info("", e);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(Api.ERROR(ArticleErrorCode.ARTICLE_NOT_FOUND));
    }

}
