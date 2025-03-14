package chat.adapter.output.persistence.repository.document;

import chat.domain.dto.UserChatSaveForm;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "user_chat")
@CompoundIndex(def = "{'userId': 1, 'chatRoomId': 1}", unique = true) // 복합키 설정
public class UserChatDocument {

    @Id
    private String id;

    private String userId;

    private String chatRoomId;

    @Builder.Default()
    private Boolean mute = false;

    @Builder.Default()
    private Boolean favorite = false;

    @Builder.Default()
    private LocalDateTime lastChatAt = LocalDateTime.now();

    public static UserChatDocument of(UserChatSaveForm userChatSaveForm) {
        return  UserChatDocument.builder()
            .userId(userChatSaveForm.getUserId())
            .chatRoomId(userChatSaveForm.getChatRoomId())
            .build();
    }

}
