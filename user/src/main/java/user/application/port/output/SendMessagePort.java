package user.application.port.output;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

public interface SendMessagePort {

    void sendMail(MimeMessage mimeMessage) throws MessagingException;

}
