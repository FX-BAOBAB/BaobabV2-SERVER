package user.application;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMessage.RecipientType;
import java.text.DecimalFormat;
import java.util.Random;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import user.application.port.output.SendMessagePort;

@Slf4j
@Service
@RequiredArgsConstructor
public class MailService {

    private final SendMessagePort sendMessagePort;

    private final JavaMailSender javaMailSender; // 의존성 주입 문제가 있는 경우 IntelliJ 문제이므로 무시

    @Async("mailExecutor")
    public void sendMail(String email, String verificationCode) {
        try {
            MimeMessage message = generateMessage(email, verificationCode);
            sendMessagePort.sendMail(message);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    private MimeMessage generateMessage(String email, String verificationCode) throws MessagingException {

        MimeMessage message = javaMailSender.createMimeMessage();
        message.setRecipients(RecipientType.TO, email);
        message.setSubject("[BAOBAB] 이메일 인증 번호 발송");

        String body = "<html><body style='background-color:#ffffff; max-width:600px; margin:0 auto; padding:40px; font-family:sans-serif;'>";
        body += "<h1 style='color:#4D6EF4;'>BAOBAB 이메일 인증</h1>";
        body += "<p style='font-size:16px; line-height:1.6;'>안녕하세요, BAOBAB입니다.<br />";
        body += "서비스 이용을 위해 이메일 주소 확인이 필요합니다.<br />";
        body += "아래 인증번호를 입력해주세요.</p>";
        body += "<div style='margin:30px auto; font-size:30px; text-align:center; padding:20px; background:#f4f4f4; border-radius:8px; color:#000000; font-weight:bold; letter-spacing:5px;'>"
            + verificationCode + "</div>";
        body += "<p style='font-size:12px; color:#888888; text-align:center; margin-top:40px;'>※ 본 메일은 발신전용입니다. 궁금한 점은 BAOBAB 고객센터로 문의해주세요.</p>";
        body += "</body></html>";

        message.setText(body, "UTF-8", "html");
        return message;
    }

}
