package chat.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ChatRoomSaveForm {

    private String title;

    private String articleId;

    private ArticleImage thumbnail;

    public static ChatRoomSaveForm of(String title, String articleId, ArticleImage articleImage) {
        return ChatRoomSaveForm.builder()
            .title(title)
            .articleId(articleId)
            .thumbnail(articleImage)
            .build();
    }
}
