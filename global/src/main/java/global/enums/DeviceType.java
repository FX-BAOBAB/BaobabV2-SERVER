package global.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DeviceType {

    iOS("아이폰"),
    WEB("웹")
    ;

    private final String description;

}
