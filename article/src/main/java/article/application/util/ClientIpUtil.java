package article.application.util;

import jakarta.servlet.http.HttpServletRequest;
import java.util.Arrays;
import org.springframework.stereotype.Component;

@Component
public class ClientIpUtil {

    private static final String[] IP_HEADER = {
        "X-Forwarded-For",
        "Proxy-Client-IP",
        "WL-Proxy-Client-IP",
        "HTTP_X_FORWARDED_FOR",
        "X-Real-IP",
        "X-RealIP",
        "REMOTE_ADDR"
    };

    public String getClientIp(HttpServletRequest request) {
        return Arrays.stream(IP_HEADER)
            .map(request::getHeader)
            .filter(this::isValidIp)
            .map(ip -> ip.split(",")[0].trim())
            .findFirst()
            .orElseGet(request::getRemoteAddr);
    }


    private boolean isValidIp(String ip) {
        return ip != null && !ip.isEmpty() && !"unknown".equalsIgnoreCase(ip);
    }

}