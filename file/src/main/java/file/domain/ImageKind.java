package file.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ImageKind {

    ARTICLE("article"),
    USER("profile"),
    USER_DEFAULT("default user image")
    ;

    private final String description;
}
