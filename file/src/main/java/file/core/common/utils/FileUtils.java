package file.core.common.utils;

public class FileUtils {
    public static String getExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf("."));
    }

    public static String getFileOfName(String fileName) {
        return fileName.substring(0, fileName.lastIndexOf("."));
    }
}
