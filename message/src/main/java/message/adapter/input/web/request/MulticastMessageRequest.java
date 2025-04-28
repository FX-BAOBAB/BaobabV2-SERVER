package message.adapter.input.web.request;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MulticastMessageRequest {

    private String title;

    private String body;

    private List<String> userIds;

}
