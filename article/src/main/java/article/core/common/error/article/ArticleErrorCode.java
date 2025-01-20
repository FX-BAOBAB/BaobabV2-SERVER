package article.core.common.error.article;

import global.errorcode.ErrorCodeIfs;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ArticleErrorCode implements ErrorCodeIfs {

    ARTICLE_NOT_FOUND(HttpStatus.NOT_FOUND.value(), 1200, "요청하신 게시글을 찾을 수 없습니다."),
    NOT_PERMITTED(HttpStatus.FORBIDDEN.value(), 1201, "권한이 없습니다.")
    ;

    private final Integer httpCode;
    private final Integer errorCode;
    private final String description;

}