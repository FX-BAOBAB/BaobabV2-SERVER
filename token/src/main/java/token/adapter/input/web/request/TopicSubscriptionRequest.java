package token.adapter.input.web.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TopicSubscriptionRequest {

    private String topic;

    private String token;

}
