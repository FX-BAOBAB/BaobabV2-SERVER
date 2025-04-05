package message.domain.command;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MessageCommand {

    private String title;

    private String body;

    private String userId;

    public static MessageCommand of(String title, String body, String userId) {
        return MessageCommand.builder()
                .title(title)
                .body(body)
                .userId(userId)
                .build();
    }

}
