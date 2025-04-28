package message.adapter.input.web.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import message.adapter.output.persistence.enums.DeviceType;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FcmTokenSavingRequest {

    private String token;

    private DeviceType deviceType;

}
