package user.security.jwt.ifs;

import java.util.Map;
import user.security.jwt.model.TokenDto;

public interface TokenHelperIfs {

    TokenDto issueAccessToken(Map<String,Object> data);
    TokenDto issueRefreshToken(Map<String,Object> data);
    Map<String, Object> validationTokenWithThrow(String token);

}
