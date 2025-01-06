package file.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Getter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class ImageCommand {
    private String id;
    private MultipartFile file;
    private ImageKind kind;
}
