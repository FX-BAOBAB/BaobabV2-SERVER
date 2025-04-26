package token.application.port.input;

import token.adapter.input.web.response.SubscriptionResponse;
import token.domain.command.TopicSubscriptionCommand;

/**
 * Subscribe Topic Input Port
 */
public interface SubscribeTopicUseCase {

    /**
     * Subscribe to Topic
     *
     * @param command Topic Subscription Command
     * @return SubscriptionResponse
     */
    SubscriptionResponse subscribeToTopic(TopicSubscriptionCommand command);

}
