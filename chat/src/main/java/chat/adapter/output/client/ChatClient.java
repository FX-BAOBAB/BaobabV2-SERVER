package chat.adapter.output.client;

import chat.domain.ChatMessage;
import java.net.URI;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "chat-service", url = "http://chat-service")
public interface ChatClient {

    @PostMapping("/feign/message")
    void sendMessage(URI chatServerURI, @RequestBody ChatMessage chatMessage);

}
