package chat.adapter.input.consumer;

import chat.application.MessageConsumerService;
import chat.application.port.input.MessageConsumerUseCase;
import chat.domain.ChatMessage;
import global.message.MessageConsumer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ChatMessageConsumer implements MessageConsumer<ChatMessage> {

    private final MessageConsumerService messageConsumerService;

    private static final String TOPIC_NAME = "chatMessage";

    @Override
    @KafkaListener(topics = TOPIC_NAME)
    public void consumeMessage(ChatMessage message) {
        log.info("수신 메시지 : {}", message);
        messageConsumerService.consumeMessage(message);
    }

}