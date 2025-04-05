package token.application.port.input;

import token.adapter.input.web.response.SubscriptionResponse;
import token.domain.command.TopicSubscriptionCommand;

public interface SubscribeTopicUseCase {

    SubscriptionResponse subscribeToTopic(TopicSubscriptionCommand command);

}
