package user.application;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.javamail.JavaMailSender;
import user.application.port.output.SendMessagePort;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MailServiceTest {

    @Mock private SendMessagePort sendMessagePort;
    @Mock private JavaMailSender javaMailSender;

    @InjectMocks private MailService mailService;

    @Captor private ArgumentCaptor<MimeMessage> mimeMessageCaptor;

    @Test
    void SMTP_메일_전송_성공() throws MessagingException {

        // Given
        String email = "baobab12@baobab.com";
        String verificationCode = "12345";

        MimeMessage mockMessage = mock(MimeMessage.class);
        when(javaMailSender.createMimeMessage()).thenReturn(mockMessage);

        // When
        mailService.sendVerificationMail(email, verificationCode);

        // Then
        verify(sendMessagePort, times(1)).sendMail(mimeMessageCaptor.capture());

        MimeMessage capturedMessage = mimeMessageCaptor.getValue();
        assertThat(capturedMessage).isNotNull();
        verify(mockMessage).setRecipients(MimeMessage.RecipientType.TO, email);

    }

}