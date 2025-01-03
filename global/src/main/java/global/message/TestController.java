package global.message;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/test/kafka")
public class TestController {

    private final MessageProducer producer;

    @PostMapping("/produce")
    public String produce() {
        producer.send("test-topic", UserMessage.builder()
            .id(1L).username("shin").build());
        return "ok";
    }


}
