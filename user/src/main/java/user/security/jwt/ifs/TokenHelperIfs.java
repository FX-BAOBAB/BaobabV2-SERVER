package user.security.jwt.ifs;

import global.enums.DeviceType;
import java.util.Map;
import user.security.jwt.model.TokenDto;

public interface TokenHelperIfs {

    TokenDto issueAccessToken(Map<String,Object> data);
    TokenDto issueRefreshToken(Map<String,Object> data, DeviceType deviceType);
    Map<String, Object> validationTokenWithThrow(String token);

}
