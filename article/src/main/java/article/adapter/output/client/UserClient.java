package article.adapter.output.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "userClient", url = "http://localhost:8082")
public interface UserClient {

    @GetMapping("/nickname")
    String getNickname(@RequestParam("userId") String userId);
}