package chat.application;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ChatRoomGeneratorTest {

    @Test
    void 채팅방_제목_생성_성공() {
        // Given
        ChatRoomGenerator chatRoomGenerator = new ChatRoomGenerator();

        // When
        String generatedTitle = chatRoomGenerator.generateDefaultTitle(List.of("닉네임1", "닉네임2", "닉네임3"));

        // Then
        assertEquals("닉네임1, 닉네임2, 닉네임3의 채팅방", generatedTitle);
    }


    @Test
    void 채팅방_제목_생성_100명_성공() {
        // Given
        ChatRoomGenerator chatRoomGenerator = new ChatRoomGenerator();

        List<String> nickNameList = new ArrayList<>();
        for (int i = 1; i <= 100; i++) {
            nickNameList.add("닉네임" + i);
        }

        // When
        String generatedTitle = chatRoomGenerator.generateDefaultTitle(nickNameList);

        // Then
        assertEquals("닉네임1, 닉네임2, 닉네임3, 닉네임4, 닉네임5 외 95명의 채팅방", generatedTitle);
    }


}