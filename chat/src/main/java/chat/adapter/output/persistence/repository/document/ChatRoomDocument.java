package chat.adapter.output.persistence.repository.document;

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

    private String title;

    private String thumbnailId;

}
