package file.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bson.types.ObjectId;

@Getter
@AllArgsConstructor
public class ImageResponse {
    private ObjectId id;
    private String url;
}
