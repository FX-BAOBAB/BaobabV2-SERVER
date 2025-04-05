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
public class MulticastMessageCommand {

    private String title;

    private String body;

    private List<String> userIds;

    public static MulticastMessageCommand of(String title, String body, List<String> userIds) {
        return MulticastMessageCommand.builder()
                .title(title)
                .body(body)
                .userIds(userIds)
                .build();
    }

}
