package user.core.common.error;

import global.errorcode.ErrorCodeIfs;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserErrorCode implements ErrorCodeIfs {

    EXIST_USER(403, 1150, "이미 존재하는 계정입니다."),
    ;


    private final Integer httpCode;

    private final Integer errorCode;

    private final String description;

}
