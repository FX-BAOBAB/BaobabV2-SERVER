package file.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class ImageListCommand {
    private String id;
    private MultipartFile[] files;
    private ImageKind imageKind;
}
