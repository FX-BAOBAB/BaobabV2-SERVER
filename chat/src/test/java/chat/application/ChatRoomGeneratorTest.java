package chat.application;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ChatRoomGeneratorTest {

    @Test
    void 채팅방_제목_생성_성공() {

        ChatRoomGenerator chatRoomGenerator = new ChatRoomGenerator();

        String generatedTitle = chatRoomGenerator.generateTitle(List.of("닉네임1", "닉네임2", "닉네임3"));

        assertEquals("닉네임1, 닉네임2, 닉네임3의 채팅방", generatedTitle);

    }

}