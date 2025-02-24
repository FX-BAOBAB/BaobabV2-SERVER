package chat.adapter.output.persistence.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MessageType {

    TEXT("텍스트 메시지"),
    IMAGE("이미지 메시지")
    ;

    private final String description;

}
