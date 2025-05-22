package message.adapter.input.web;

import global.annotation.AuthenticatedUser;
import global.api.Api;
import global.resolver.AuthUser;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import message.adapter.input.web.request.FcmTokenSavingRequest;
import message.adapter.input.web.request.TopicSubscriptionRequest;
import message.adapter.input.web.response.SubscriptionResponse;
import message.application.port.input.DeleteFcmTokenUseCase;
import message.application.port.input.SaveFcmTokenUseCase;
import message.application.port.input.SubscribeTopicUseCase;
import message.domain.command.FcmTokenSaveCommand;
import message.domain.command.TopicSubscriptionCommand;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class FcmTokenApiController {

    private final SaveFcmTokenUseCase saveFcmTokenUseCase;

    private final DeleteFcmTokenUseCase deleteFcmTokenUseCase;

    private final SubscribeTopicUseCase subscribeTopicUseCase;


    @PostMapping("/save")
    public Api<Boolean> save(
        @Valid @RequestBody FcmTokenSavingRequest request,
        @AuthenticatedUser AuthUser authUser) {

        FcmTokenSaveCommand command = FcmTokenSaveCommand.of(request, authUser.getUserId());
        return Api.OK(saveFcmTokenUseCase.saveFcmToken(command));
    }

    @PostMapping("/subscribe")
    public Api<SubscriptionResponse> subscribe(
        @Valid @RequestBody TopicSubscriptionRequest request,
        @AuthenticatedUser AuthUser authUser) {

        TopicSubscriptionCommand command = TopicSubscriptionCommand.of(request);
        return Api.OK(subscribeTopicUseCase.subscribeToTopic(command));
    }

    @DeleteMapping("/{token}")
    public Api<Boolean> deleteFcmToken(@PathVariable String token) {
        return Api.OK(deleteFcmTokenUseCase.deleteFcmToken(token));
    }

}
