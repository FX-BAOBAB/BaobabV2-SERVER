package global.converter;

import global.annotation.Converter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Converter
public class KSTConverter {

    private static final int KST_OFFSET_HOURS = 9;

    private static final DateTimeFormatter KST_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS");

    public String convertToKST(LocalDateTime localDateTime) {

        LocalDateTime dateTime = localDateTime.plusHours(KST_OFFSET_HOURS);

        return dateTime.format(KST_FORMATTER);

    }

}
