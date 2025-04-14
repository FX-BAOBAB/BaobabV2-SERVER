package token.adapter.input.web.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import token.adapter.output.persistence.enums.DeviceType;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FcmTokenSavingRequest {

    private String token;

    private DeviceType deviceType;

}
