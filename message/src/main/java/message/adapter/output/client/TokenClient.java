package message.adapter.output.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "baobab-fcm-tokens")
public interface TokenClient {

    @PostMapping("/fcm-tokens")
    List<String> getFcmTokens(@RequestBody List<String> userIds);

}