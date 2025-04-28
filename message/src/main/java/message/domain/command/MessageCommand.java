package message.domain.command;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import message.adapter.input.web.request.MessageRequest;
import message.adapter.input.web.request.MulticastMessageRequest;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MessageCommand {

    private String title;

    private String body;

    private List<String> userIds;

    public static MessageCommand of(MessageRequest request) {
        return MessageCommand.builder()
                .title(request.getTitle())
                .body(request.getBody())
                .userIds(List.of(request.getUserId()))
                .build();
    }

    public static MessageCommand of(MulticastMessageRequest request) {
        return MessageCommand.builder()
                .title(request.getTitle())
                .body(request.getBody())
                .userIds(request.getUserIds())
                .build();
    }

}
