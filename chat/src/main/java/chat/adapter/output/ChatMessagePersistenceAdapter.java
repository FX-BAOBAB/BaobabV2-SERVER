package chat.adapter.output;

import chat.adapter.output.persistence.repository.MessageMongoRepository;
import chat.adapter.output.persistence.repository.MessageQueryRepository;
import chat.adapter.output.persistence.repository.document.MessageDocument;
import chat.application.port.output.ChatMessagePersistencePort;
import chat.domain.dto.ChatMessageSaveForm;
import chat.domain.dto.ChatMessageSearchForm;
import global.annotation.output.PersistenceAdapter;
import java.util.List;
import lombok.RequiredArgsConstructor;

@PersistenceAdapter
@RequiredArgsConstructor
public class ChatMessagePersistenceAdapter implements ChatMessagePersistencePort {

    private final MessageMongoRepository messageMongoRepository;
    private final MessageQueryRepository messageQueryRepository;

    @Override
    public MessageDocument saveMessage(ChatMessageSaveForm chatMessageSaveForm) {
        return messageMongoRepository.save(MessageDocument.of(chatMessageSaveForm));
    }

    @Override
    public List<MessageDocument> getMessages(ChatMessageSearchForm chatMessageSearchForm) {
        return messageQueryRepository.getMessages(chatMessageSearchForm);
    }

}
