package token.domain.command;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import token.adapter.input.web.request.TopicSubscriptionRequest;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TopicSubscriptionCommand {

    private String topic;

    private String token;

    public static TopicSubscriptionCommand of(TopicSubscriptionRequest request) {
        return TopicSubscriptionCommand.builder()
                .topic(request.getTopic())
                .token(request.getToken())
                .build();
    }

}
