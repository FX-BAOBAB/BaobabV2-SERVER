package file.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Getter
@ToString
@Builder
@AllArgsConstructor
@Document(collection = "image")
public class ImageMetaData {
    @Id
    private ObjectId id;
    @Field("image_url")
    private String url;
    @Field("original_name")
    private String originalName;
    @Field("server_name")
    private String serverName;
    @Field("extension")
    private String extension;
    @Field("kind")
    private ImageKind kind;
}
