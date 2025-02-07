package gateway.common.error;

import global.errorcode.ErrorCodeIfs;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum TokenErrorCode implements ErrorCodeIfs {

    INVALID_TOKEN(HttpStatus.UNAUTHORIZED.value(), 1102,"유효하지 않은 토큰입니다."),
    EXPIRED_TOKEN(HttpStatus.UNAUTHORIZED.value(),1103,"만료된 토큰입니다."),
    TOKEN_EXCEPTION(HttpStatus.UNAUTHORIZED.value(),1104,"알 수 없는 토큰 에러입니다."),
    NOT_PERMITTED(HttpStatus.FORBIDDEN.value(),1105,"허용되지 않은 접근입니다.")
    ;

    private final Integer httpCode;
    private final Integer errorCode;
    private final String description;

}
