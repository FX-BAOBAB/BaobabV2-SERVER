package user.domain.form;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;
import user.adapter.input.web.request.EmailVerificationRequest;

class EmailVerificationFormTest {

    @Test
    void emailVerificationForm_변환_테스트() {

        // Given
        EmailVerificationRequest emailVerificationRequest = new EmailVerificationRequest(
            "baobab12@baobab.com", "12345");

        // When
        EmailVerificationForm emailVerificationForm = EmailVerificationForm.of(
            emailVerificationRequest);

        // Then
        assertThat(emailVerificationForm.getEmail()).isEqualTo("baobab12@baobab.com");
        assertThat(emailVerificationForm.getVerificationCode()).isEqualTo("12345");

    }

}
