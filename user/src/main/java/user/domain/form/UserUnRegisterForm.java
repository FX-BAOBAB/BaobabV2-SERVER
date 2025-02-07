package user.domain.form;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import user.adapter.output.persistence.enums.UserStatus;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserUnRegisterForm {

    private String userId;

    private UserStatus status;

    private LocalDateTime unRegisterAt;

    public static UserUnRegisterForm of(String userId) {
        return UserUnRegisterForm.builder()
            .userId(userId)
            .status(UserStatus.UNREGISTERED)
            .unRegisterAt(LocalDateTime.now())
            .build();
    }

}
