package message.adapter.input.web.request;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import message.adapter.output.persistence.enums.DeviceType;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FcmTokenSavingRequest {

    @NotBlank(message = "null 값 또는 공백이 포함될 수 없습니다.")
    private String token;

    @NotNull(message = "null 값일 수 없습니다.")
    private DeviceType deviceType;

}
