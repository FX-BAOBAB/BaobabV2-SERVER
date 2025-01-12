package file.core.common.converter;

import file.domain.ImageCommand;
import file.domain.ImageKind;
import global.annotation.Converter;
import org.springframework.web.multipart.MultipartFile;

@Converter
public class ImageConverter {

    public ImageCommand toImageCommand(String id, MultipartFile file, ImageKind kind) {
        return ImageCommand.builder()
            .id(id)
            .file(file)
            .kind(kind)
            .build();
    }

}
