package file.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ImageKind {

    ARTICLE("article"),
    USER("profile"),
    ;

    private final String description;
}
