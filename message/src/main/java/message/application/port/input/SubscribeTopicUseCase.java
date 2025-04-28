package message.application.port.input;

import message.adapter.input.web.response.SubscriptionResponse;
import message.domain.command.TopicSubscriptionCommand;

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
