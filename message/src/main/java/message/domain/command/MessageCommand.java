package message.domain.command;

import java.util.List;
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

    private List<String> userIds;

    public static MessageCommand of(String title, String body, String userId) {
        return MessageCommand.builder()
                .title(title)
                .body(body)
                .userIds(List.of(userId))
                .build();
    }

    public static MessageCommand of(String title, String body, List<String> userIds) {
        return MessageCommand.builder()
                .title(title)
                .body(body)
                .userIds(userIds)
                .build();
    }

}
