package file.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@AllArgsConstructor
public class ImageCommand {
    private MultipartFile file;
    private ImageKind kind;
}
