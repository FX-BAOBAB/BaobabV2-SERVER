package user.domain.form;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;

import java.time.LocalDateTime;
import user.adapter.output.persistence.enums.UserStatus;

class UserUnRegisterFormTest {

    @Test
    void userUnRegisterForm_변환_테스트() {

        // Given
        String userId = "user123";

        // When
        UserUnRegisterForm userUnRegisterForm = UserUnRegisterForm.of(userId);

        // Then
        assertThat(userUnRegisterForm.getUserId()).isEqualTo(userId);
        assertThat(userUnRegisterForm.getStatus()).isEqualTo(UserStatus.UNREGISTERED);
        assertThat(userUnRegisterForm.getUnRegisterAt()).isBeforeOrEqualTo(LocalDateTime.now()); // 현재 시간보다 빠르거나 같아야 함
    }

}
