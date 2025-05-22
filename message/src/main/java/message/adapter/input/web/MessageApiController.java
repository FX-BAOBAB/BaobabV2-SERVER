package message.adapter.input.web;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import message.adapter.input.web.request.MessageRequest;
import message.adapter.input.web.request.MulticastMessageRequest;
import message.application.port.input.SendMessageUseCase;
import message.domain.command.MessageCommand;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class MessageApiController {

    private final SendMessageUseCase sendMessageUseCase;

    @PostMapping("/messages")
    public void sendMessage(@Valid @RequestBody MessageRequest request) {
        sendMessageUseCase.send(MessageCommand.of(request));
    }

    @PostMapping("/messages/multicast")
    public void sendMultipleMessages(@Valid @RequestBody MulticastMessageRequest request) {
        sendMessageUseCase.send(MessageCommand.of(request));
    }

}
