package file.adapter.input;

import file.domain.ImageCommand;
import global.message.MessageConsumer;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KafkaImageConsumer implements MessageConsumer<ImageCommand> {
    @KafkaListener(topics = "temp")
    @Override
    public ImageCommand consumeMessage(ImageCommand imageCommand) {
        return imageCommand;
    }
}
