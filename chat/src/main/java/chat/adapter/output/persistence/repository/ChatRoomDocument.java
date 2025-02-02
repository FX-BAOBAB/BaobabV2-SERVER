package chat.adapter.output.persistence.repository;

import chat.domain.dto.ChatRoomSaveForm;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "chat_room")
public class ChatRoomDocument {

    @Id
    private String id;

    private String articleId;

    private String buyerId;

    private LocalDateTime registeredAt;

    public static ChatRoomDocument of(ChatRoomSaveForm chatRoomSaveForm) {
        return ChatRoomDocument.builder()
            .articleId(chatRoomSaveForm.getArticleId())
            .buyerId(chatRoomSaveForm.getBuyerId())
            .registeredAt(chatRoomSaveForm.getRegisteredAt())
            .build();
    }

}
