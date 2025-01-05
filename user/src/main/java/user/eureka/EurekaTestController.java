package user.eureka;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@Slf4j
public class EurekaTestController {

    Environment env;

    public EurekaTestController(Environment env){
        this.env = env;
    }

    @GetMapping("/welcome")
    public String welcome(){
        return "Welcome First Service";
    }

    @GetMapping("/message")
    public String message(@RequestHeader("first-request") String header){
        log.info(header);
        return "Hello First Service";
    }

    @GetMapping("/check")
    public String check(HttpServletRequest request){
        // 포트를 가져오는 방법 두 가지
        log.info("Server port={}", request.getServerPort());
        return String.format("Check First Service on PORT %s", env.getProperty("local.server.port"));
    }

}
