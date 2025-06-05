package message.adapter.input.web.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FcmTokenDeletionRequest {

    @NotBlank(message = "null 값 또는 공백이 포함될 수 없습니다.")
    private String token;

}
