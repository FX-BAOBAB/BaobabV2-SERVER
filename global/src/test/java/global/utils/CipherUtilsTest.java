package global.utils;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class CipherUtilsTest {

    @Autowired
    private CipherUtils cipherUtils;

    @Test
    void generateImageId() {
        String plainText = "ART-" + "123456fdsaf562342-" + System.nanoTime();
        String imageId = null;

        try {
            imageId = cipherUtils.encrypt(plainText);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        assertNotNull(imageId); // 결과가 null이 아닌지 확인

        String decodeId = null;

        try {
            decodeId = cipherUtils.decrypt(imageId);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        assertNotNull(decodeId); // 결과가 null이 아닌지 확인
        assertEquals(plainText, decodeId); // 평문과 복호화된 값과 같은지 확인

    }
}
