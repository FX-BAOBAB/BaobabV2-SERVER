package chat.core.common.error;

import global.errorcode.ErrorCodeIfs;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ChatRoomErrorCode  implements ErrorCodeIfs {

    CHAT_ROOM_EXISTS(HttpStatus.CONFLICT.value(), 1400, "이미 존재하는 채팅방입니다.")
    ;

    private final Integer httpCode;
    private final Integer errorCode;
    private final String description;

}