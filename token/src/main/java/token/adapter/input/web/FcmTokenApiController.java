package token.adapter.input.web;

import global.annotation.AuthenticatedUser;
import global.api.Api;
import global.resolver.AuthUser;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import token.adapter.input.web.request.FcmTokenSavingRequest;
import token.adapter.input.web.request.TopicSubscriptionRequest;
import token.adapter.input.web.response.SubscriptionResponse;
import token.application.port.input.GetFcmTokenUseCase;
import token.application.port.input.SaveFcmTokenUseCase;
import token.application.port.input.SubscribeTopicUseCase;
import token.domain.command.FcmTokenSaveCommand;
import token.domain.command.TopicSubscriptionCommand;

@Slf4j
@RestController
@RequiredArgsConstructor
public class FcmTokenApiController {

    private final SaveFcmTokenUseCase saveFcmTokenUseCase;

    private final SubscribeTopicUseCase subscribeTopicUseCase;

    @PostMapping("/save")
    public Api<Boolean> save(
        @RequestBody FcmTokenSavingRequest request,
        @AuthenticatedUser AuthUser authUser) {

        FcmTokenSaveCommand command = FcmTokenSaveCommand.of(request, authUser.getUserId());
        return Api.OK(saveFcmTokenUseCase.saveFcmToken(command));
    }

    @PostMapping("/subscribe")
    public Api<SubscriptionResponse> subscribe(
        @RequestBody TopicSubscriptionRequest request) {

        TopicSubscriptionCommand command = TopicSubscriptionCommand.of(request);
        return Api.OK(subscribeTopicUseCase.subscribeToTopic(command));
    }

}
