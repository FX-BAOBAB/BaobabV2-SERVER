package chat.adapter.output.client;

import chat.adapter.output.client.dto.MulticastMessageRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "baobab-messages", url = "${feign.message-service.url}")
public interface MessageClient {

    @PostMapping("/messages/multicast")
    void sendMultipleMessages(@RequestBody MulticastMessageRequest request);

}
