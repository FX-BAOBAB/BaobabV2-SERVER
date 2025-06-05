package message.core.common.error;

import global.errorcode.ErrorCodeIfs;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum MessageErrorCode implements ErrorCodeIfs {

    FIREBASE_INITIALIZATION_FAILED(HttpStatus.INTERNAL_SERVER_ERROR.value(), 1500, "FirebaseApp 초기화에 실패했습니다."),
    FAILED_TO_SUBSCRIBE_TOPIC(HttpStatus.INTERNAL_SERVER_ERROR.value(), 1501, "토픽 구독에 실패했습니다."),
    NOT_FOUND_FCM_TOKEN(HttpStatus.NOT_FOUND.value(), 1502, "FCM 토큰을 찾을 수 없습니다."),
    FAILED_TO_UNSUBSCRIBE_TOPIC(HttpStatus.INTERNAL_SERVER_ERROR.value(), 1502, "토픽 구독 취소에 실패했습니다."),
    FAILED_TO_SEND_MESSAGE(HttpStatus.INTERNAL_SERVER_ERROR.value(), 1550, "메시지 전송에 실패했습니다."),
    FAILED_TO_RESEND_MESSAGE(HttpStatus.INTERNAL_SERVER_ERROR.value(), 1551, "메시지 재전송에 실패했습니다.")
    ;

    private final Integer httpCode;
    private final Integer errorCode;
    private final String description;

}