package chat.core.common.error;

import global.errorcode.ErrorCodeIfs;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ChatErrorCode implements ErrorCodeIfs {

    CHAT_ROOM_NOT_FOUND(404, 1400, "존재하지 않는 채팅방입니다."),
    ;

    private final Integer httpCode;
    private final Integer errorCode;
    private final String description;

}
