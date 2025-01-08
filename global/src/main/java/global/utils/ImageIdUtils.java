package global.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class ImageIdUtils {

    private final CipherUtils cipherUtils;

    // TODO 예외 처리
    public String generateImageId(String module, String userId) {
         String imageId = module + "-" + userId + "-" + System.nanoTime();
        try {
            return cipherUtils.encrypt(imageId);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // TODO 예외 처리
    public String decodeImageId(String imageId) {
        try {
            return cipherUtils.decrypt(imageId);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}

