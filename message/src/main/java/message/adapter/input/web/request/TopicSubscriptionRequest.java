package message.adapter.input.web.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TopicSubscriptionRequest {

    @Pattern(
        regexp = "^(/topics/)?(private/)?[a-zA-Z0-9-_.~%]+$",
        message = "입력 값 형식이 올바르지 않습니다."
    )
    @NotBlank(message = "null 값 또는 공백이 포함될 수 없습니다.")
    private String topic;

    @NotBlank(message = "null 값 또는 공백이 포함될 수 없습니다.")
    private String token;

}
