package user.adapter.output.persistence.message;

import jakarta.mail.Message.RecipientType;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import user.application.port.output.SendMessagePort;

@Slf4j
@Component
@RequiredArgsConstructor
public class SmtpMailSender implements SendMessagePort {

    private final JavaMailSender javaMailSender; // 의존성 주입 문제가 있는 경우 IntelliJ 문제이므로 무시

    private static final int RETRY_COUNT = 3;
    private static final long INITIAL_BACKOFF = 1000; // 1초

    @Async("mailExecutor")
    public void sendMail(MimeMessage mimeMessage) throws MessagingException {
        sendWithRetry(mimeMessage, RETRY_COUNT, INITIAL_BACKOFF);
    }

    /**
     * 최초 1회 + 재시도 3회 = 총 4회
     * 최대 8초 대기 후 전송
     * @param mimeMessage
     * @param retryCount
     * @param backoffMillis
     */
    private void sendWithRetry(MimeMessage mimeMessage, int retryCount, long backoffMillis)
        throws MessagingException {
        if (retryCount < 0) {
            log.error("재시도 횟수 초과 : {}", (Object) mimeMessage.getRecipients(RecipientType.TO));
            return;
        }

        try {
            javaMailSender.send(mimeMessage);
        } catch (Exception e) {

            if (e.getMessage() != null && e.getMessage().contains("553")) {
                log.error("유효하지 않은 이메일 형식입니다. : {}", mimeMessage.getRecipients(RecipientType.TO));
                return;
            }

            log.warn("이메일 전송 실패 (남은 재시도: {}, 대기: {}ms) : {} 원인: {}", retryCount, backoffMillis,
                mimeMessage.getRecipients(RecipientType.TO), e.getMessage());

            try {
                Thread.sleep(backoffMillis);
            } catch (InterruptedException ie) {
                Thread.currentThread().interrupt();
                log.error("백오프 대기 중 인터럽트 발생", ie);
                return;
            }

            sendWithRetry(mimeMessage, retryCount - 1, backoffMillis * 2);
        }
    }

}
