package chat.application;

import static org.junit.jupiter.api.Assertions.*;

import chat.application.factory.ChatRoomTitleGenerator;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ChatRoomGeneratorTest {

    @Autowired
    private ChatRoomTitleGenerator chatRoomTitleGenerator;

    @Test
    void 채팅방_제목_생성_성공() {
        // Given

        // When
        String generatedTitle = chatRoomTitleGenerator.getChatRoomTitle(List.of("1000L", "2000L"));

        // Then
        assertEquals("1000L, 2000L의 채팅방", generatedTitle);
    }


    @Test
    void 채팅방_제목_생성_100명_성공() {

        // Given
        List<String> nickNameList = new ArrayList<>();
        for (int i = 1; i <= 100; i++) {
            nickNameList.add("user" + i);
        }

        // When
        String generatedTitle = chatRoomTitleGenerator.getChatRoomTitle(nickNameList);

        // Then
        assertEquals("user1님의 단톡방", generatedTitle);
    }


}