package file.application.port.output.utils;

import org.springframework.util.StringUtils;

import java.util.Objects;
import java.util.UUID;

public class FileUtils {

    public static String extractFileExtension(String fileName) {
        String cleanedFileName = StringUtils.cleanPath(Objects.requireNonNull(fileName));
        return cleanedFileName.substring(cleanedFileName.lastIndexOf("."));
    }
}
