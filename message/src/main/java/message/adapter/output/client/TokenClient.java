package message.adapter.output.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "baobab-fcm-tokens")
public interface TokenClient {

    @GetMapping("/{userId}")
    String getFcmToken(@PathVariable("userId") String userId);

    @PostMapping("/fcm-tokens")
    List<String> getFcmTokens(@RequestBody List<String> userIds);

}