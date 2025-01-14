package global.converter;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;

class KSTConverterTest {

    @Test
    void convertToKST() {

        KSTConverter converter = new KSTConverter();

        LocalDateTime utcDateTime = LocalDateTime.of(2025, 1, 15, 0, 0, 0);

        String expectedKST = "2025-01-15T09:00:00.000";

        String result = converter.convertToKST(utcDateTime);

        assertEquals(expectedKST, result);
    }
}