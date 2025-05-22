package message.adapter.input.web.request;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MessageRequest {

    @NotNull(message = "null 값일 수 없습니다.")
    private String title;

    @NotNull(message = "null 값일 수 없습니다.")
    private String body;

    @NotBlank(message = "null 값 또는 공백이 포함될 수 없습니다.")
    private String userId;

}
