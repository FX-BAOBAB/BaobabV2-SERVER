package user.application.email;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMessage.RecipientType;
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
    public void sendVerificationMail(String email, String verificationCode) {
        try {
            MimeMessage message = generateVerificationMessage(email, verificationCode);
            sendMessagePort.sendMail(message);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    @Async("mailExecutor")
    public void sendDormantUserMail(String email, MailType mailType) {
        try {
            MimeMessage message = generateMessage(email, mailType);
            sendMessagePort.sendMail(message);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    private MimeMessage generateMessage(String email, MailType mailType) throws MessagingException {
        return switch (mailType) {
            case DORMANT_NOTICE -> generateDormantNoticeMessage(email);
            case DORMANT_WARNING -> generateDormantWarningMessage(email);
        };
    }

    private MimeMessage generateVerificationMessage(String email, String verificationCode) throws MessagingException {

        MimeMessage message = javaMailSender.createMimeMessage();
        message.setRecipients(RecipientType.TO, email);
        message.setSubject("[BAOBAB] 이메일 인증 번호 발송");

        String body = """
            <html><body style='background-color:#ffffff; max-width:600px; margin:0 auto; padding:40px; font-family:sans-serif;'>
            <h1 style='color:#4D6EF4;'>BAOBAB 이메일 인증</h1>
            <p style='font-size:16px; line-height:1.6;'>안녕하세요, BAOBAB입니다.<br />
            서비스 이용을 위해 이메일 주소 확인이 필요합니다.<br />
            아래 인증번호를 입력해주세요.</p>
            <div style='margin:30px auto; font-size:30px; text-align:center; padding:20px; background:#f4f4f4; border-radius:8px; color:#000000; font-weight:bold; letter-spacing:5px;'>""" +
                verificationCode + """
            </div>
            <p style='font-size:12px; color:#888888; text-align:center; margin-top:40px;'>※ 본 메일은 발신전용입니다. 궁금한 점은 BAOBAB 고객센터로 문의해주세요.</p>
            </body></html>
        """;

        message.setText(body, "UTF-8", "html");
        return message;
    }

    private MimeMessage generateDormantNoticeMessage(String email) throws MessagingException {

        MimeMessage message = javaMailSender.createMimeMessage();
        message.setRecipients(RecipientType.TO, email);
        message.setSubject("[BAOBAB] 회원님 계정이 휴면 상태로 전환되었습니다.");

        String body = """
            <html><body style='background-color:#ffffff; max-width:600px; margin:0 auto; padding:40px; font-family:sans-serif;'>
            <h1 style='color:#4D6EF4;'>BAOBAB 휴면 계정 안내</h1>
            <p style='font-size:16px; line-height:1.6;'>안녕하세요, BAOBAB입니다.<br />
            회원님께서 최근 1년간 로그인하지 않아 계정이 <b>휴면 상태</b>로 전환되었습니다.<br />
            다시 서비스를 이용하시려면 로그인 후 휴면 해제를 진행해주세요.</p>
            <p style='font-size:14px; line-height:1.6;'>언제나 회원님의 편리한 이용을 위해 최선을 다하겠습니다.<br />
            감사합니다.</p>
            <p style='font-size:12px; color:#888888; text-align:center; margin-top:40px;'>※ 본 메일은 발신전용입니다. 궁금한 점은 BAOBAB 고객센터로 문의해주세요.</p>
            </body></html>
        """;

        message.setText(body, "UTF-8", "html");
        return message;
    }

    private MimeMessage generateDormantWarningMessage(String email) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        message.setRecipients(RecipientType.TO, email);
        message.setSubject("[BAOBAB] 휴면 계정 전환 예정 안내");

        String body = """
            <html><body style='background-color:#ffffff; max-width:600px; margin:0 auto; padding:40px; font-family:sans-serif;'>
            <h1 style='color:#4D6EF4;'>BAOBAB 휴면 예정 안내</h1>
            <p style='font-size:16px; line-height:1.6;'>안녕하세요, BAOBAB입니다.<br />
            회원님께서 최근 1년간 로그인하지 않아, 3일 후 계정이 <b>휴면 상태</b>로 전환될 예정입니다.<br />
            계속 서비스를 이용하시려면 미리 로그인해주세요.</p>
            <p style='font-size:14px; line-height:1.6;'>언제나 회원님의 편리한 이용을 위해 최선을 다하겠습니다.<br />
            감사합니다.</p>
            <p style='font-size:12px; color:#888888; text-align:center; margin-top:40px;'>※ 본 메일은 발신전용입니다. 궁금한 점은 BAOBAB 고객센터로 문의해주세요.</p>
            </body></html>
        """;

        message.setText(body, "UTF-8", "html");
        return message;
    }

}
