package user.adapter.output.persistence.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum GenderType {

    MALE("남성"),
    FEMALE("여성")
    ;

    private final String description;

}