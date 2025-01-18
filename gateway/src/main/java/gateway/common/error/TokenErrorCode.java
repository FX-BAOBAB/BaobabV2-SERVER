package gateway.common.error;

import global.errorcode.ErrorCodeIfs;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum TokenErrorCode implements ErrorCodeIfs {

    NOT_PERMITTED(HttpStatus.FORBIDDEN.value(),1105,"허용되지 않은 접근입니다.")
    ;

    private final Integer httpCode;
    private final Integer errorCode;
    private final String description;

}
