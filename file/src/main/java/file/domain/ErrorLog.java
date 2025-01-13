package file.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.Instant;
import java.time.LocalDateTime;

@Getter
@Document(collection = "error_logs")
@Builder
public class ErrorLog {
    @Id
    private String id;
    private Integer code;
    private String timestamp; // "yyyy-mm-dd hh:mm:ss"
    @Field("is_custom_exception")
    private boolean isCustomException;
    private String description;
}