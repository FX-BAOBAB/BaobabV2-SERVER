package file.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@AllArgsConstructor
public class ImageRequest {
    private MultipartFile imageFile;
    private ImageKind kind;
}
