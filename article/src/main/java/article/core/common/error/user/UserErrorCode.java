package article.core.common.error.user;

import global.errorcode.ErrorCodeIfs;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum UserErrorCode implements ErrorCodeIfs {

    USER_NOT_FOUND(HttpStatus.NOT_FOUND.value(), 1153, "사용자를 찾을 수 없습니다."),
    ;

    private final Integer httpCode;
    private final Integer errorCode;
    private final String description;

}