package chat.adapter.output.client;

import chat.domain.dto.UserSimpleInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "baobab-users")
public interface UserClient {

    @GetMapping("/simple-info")
    UserSimpleInfo getUserSimpleInfo(@RequestParam("userId") String userId);

}
