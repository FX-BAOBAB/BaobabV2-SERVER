package message.adapter.input.web.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TopicSubscriptionRequest {

    private String topic;

    private String token;

}
