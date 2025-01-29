package user.adapter.input.web;

import global.annotation.input.RestAdapter;
import global.api.Api;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import user.adapter.input.web.request.DuplicationEmailRequest;
import user.adapter.input.web.request.DuplicationNickNameRequest;
import user.adapter.input.web.request.UserLoginRequest;
import user.adapter.input.web.request.UserRegisterRequest;
import user.adapter.input.web.response.JwtInfoResponse;
import user.adapter.input.web.response.TokenResponse;
import user.adapter.input.web.response.UserRegisterResponse;
import user.application.TokenValidationService;
import user.application.port.input.ReIssueAccessTokenUseCase;
import user.application.port.input.UserLoginUseCase;
import user.application.port.input.UserRegisterUseCase;
import user.core.common.annotation.DupleCheck;
import user.domain.command.TokenCommand;
import user.domain.command.UserLoginCommand;
import user.domain.command.UserRegisterCommand;
import user.security.jwt.model.JwtInfoDto;
import user.security.jwt.model.TokenDto;

@RestAdapter
@RequiredArgsConstructor
public class UserOpenApiController {

    private final UserRegisterUseCase userRegisterUseCase;
    private final UserLoginUseCase userLoginUseCase;
    private final ReIssueAccessTokenUseCase reIssueAccessTokenUseCase;
    private final TokenValidationService tokenValidationService;

    @PostMapping("/register")
    @DupleCheck
    public Api<UserRegisterResponse> register(
        @RequestBody @Valid Api<UserRegisterRequest> userRegisterRequest
    ) {
        UserRegisterCommand registerCommand = UserRegisterCommand.of(
            userRegisterRequest.getBody());

        String userId = userRegisterUseCase.register(registerCommand);
        UserRegisterResponse response = UserRegisterResponse.toResponse(userId);
        return Api.OK(response);
    }

    @PostMapping("/login")
    public Api<TokenResponse> login(@RequestBody @Valid Api<UserLoginRequest> userLoginRequest) {
        UserLoginCommand loginCommand = UserLoginCommand.of(userLoginRequest.getBody());
        TokenCommand tokenCommand = userLoginUseCase.login(loginCommand);
        TokenResponse response = TokenResponse.toResponse(tokenCommand);
        return Api.OK(response);
    }

    @PostMapping("/reissue")
    public Api<TokenDto> reIssueAccessToken(@RequestHeader("Authorization") String refreshToken) {
        TokenDto response = reIssueAccessTokenUseCase.reIssueAccessToken(refreshToken);
        return Api.OK(response);
    }

    @PostMapping("/duplication/email")
    @DupleCheck
    public Api<Boolean> checkDuplicateEmail(
        @RequestBody @Valid Api<DuplicationEmailRequest> duplicationEmailRequest
    ) {
        return Api.OK(true);
    }

    @PostMapping("/duplication/nickname")
    @DupleCheck
    public Api<Boolean> checkDuplicateNickName(
        @RequestBody @Valid Api<DuplicationNickNameRequest> duplicationNickNameRequest
    ) {
        return Api.OK(true);
    }

    @PostMapping("/validation")
    public Api<JwtInfoResponse> validateToken(@RequestHeader("Authorization") String accessToken) {
        JwtInfoDto jwtInfoDto = tokenValidationService.validateToken(accessToken);
        JwtInfoResponse response = JwtInfoResponse.toResponse(jwtInfoDto);
        return Api.OK(response);
    }

}
