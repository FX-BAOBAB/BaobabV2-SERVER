package user.application.email;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MailType {

    DORMANT_NOTICE("휴면 상태 전환 안내"),
    DORMANT_WARNING("휴면전환 3일 전 알림"),
    ;

    private final String description;

}
