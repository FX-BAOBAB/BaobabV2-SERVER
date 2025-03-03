package chat.adapter.output;

import chat.adapter.output.persistence.repository.MessageMongoRepository;
import chat.adapter.output.persistence.repository.document.MessageDocument;
import chat.application.port.output.ChatMessagePersistencePort;
import chat.domain.dto.ChatMessageSaveForm;
import global.annotation.output.PersistenceAdapter;
import lombok.RequiredArgsConstructor;

@PersistenceAdapter
@RequiredArgsConstructor
public class ChatMessagePersistenceAdapter implements ChatMessagePersistencePort {

    private final MessageMongoRepository messageMongoRepository;

    @Override
    public boolean saveMessage(ChatMessageSaveForm chatMessageSaveForm) {
        MessageDocument savedMessage = messageMongoRepository.save(
            MessageDocument.of(chatMessageSaveForm));
        return savedMessage.getId() != null;
    }

}
