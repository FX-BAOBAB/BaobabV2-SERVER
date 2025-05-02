package user.adapter.input.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import user.application.port.input.EmailVerificationUseCase;
import user.application.port.input.ReIssueAccessTokenUseCase;
import user.application.port.input.UserLoginUseCase;
import user.application.port.input.UserReaderUseCase;
import user.application.port.input.UserRegisterUseCase;
import user.domain.command.TokenCommand;
import user.domain.command.UserLoginCommand;
import user.domain.command.UserReaderCommand;
import user.domain.command.UserRegisterCommand;
import user.domain.form.EmailVerificationForm;
import user.security.jwt.model.TokenDto;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class UserOpenApiControllerTest {

    @InjectMocks
    private UserOpenApiController userOpenApiController;

    @Mock
    private UserRegisterUseCase userRegisterUseCase;
    @Mock
    private EmailVerificationUseCase emailVerificationUseCase;
    @Mock
    private UserLoginUseCase userLoginUseCase;
    @Mock
    private ReIssueAccessTokenUseCase reIssueAccessTokenUseCase;
    @Mock
    private UserReaderUseCase userReaderUseCase;

    private MockMvc mockMvc;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(userOpenApiController).build();
    }

    @Test
    void 회원가입API_성공() throws Exception {
        // Given: 회원가입 요청 JSON 준비
        String requestJson = """
            {
                "body": {
                    "email": "baobab12@example.com",
                    "password": "Password1",
                    "name": "오밥이",
                    "nickName": "obab2",
                    "carrierType": "SKT",
                    "phoneNumber": "010-1234-5678",
                    "genderType": "MALE",
                    "isForeigner": false,
                    "birth": "2001-09-07",
                    "address": "서울시 강남구",
                    "detailAddress": "101동 202호",
                    "basicAddress": true,
                    "post": "12345"
                }
            }
            """;

        mockMvc.perform(post("/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
            .andExpect(status().isOk());
    }

    @Test
    void 이메일_검증API_성공() throws Exception {
        String requestJson = """
            {
                "body": {
                    "email": "baobab12@baobab.com",
                    "verificationCode": "12345"
                }
            }
            """;
        mockMvc.perform(post("/email-verification")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
            .andExpect(status().isOk());
    }

    @Test
    void 로그인API_성공() throws Exception {
        String requestJson = """
            {
                "body": {
                    "email": "baobab12@baobab.com",
                    "password": "Passowrd1"
                }
            }
            """;

        TokenCommand tokenCommand = TokenCommand.builder()
            .accessToken("accessToken")
            .accessTokenExpiredAt(LocalDateTime.now().plusHours(1))
            .refreshToken("refreshToken")
            .refreshTokenExpiredAt(LocalDateTime.now().plusHours(12))
            .build();

        when(userLoginUseCase.login(any(UserLoginCommand.class))).thenReturn(tokenCommand);

        mockMvc.perform(post("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
            .andExpect(status().isOk());
    }

    @Test
    void 토큰재발급API_성공() throws Exception {
        String refreshTokenHeader = "refreshToken";

        mockMvc.perform(post("/reissue")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", refreshTokenHeader))
            .andExpect(status().isOk());
    }

    @Test
    void 이메일중복검사API_성공() throws Exception {
        String requestJson = """
            {
                "body": {
                    "email": "baobab12@example.com"
                }
            }
            """;

        mockMvc.perform(post("/duplication/email")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
            .andExpect(status().isOk());
    }

    @Test
    void 닉네임중복검사API_성공() throws Exception {
        String requestJson = """
            {
                "body": {
                    "nickName": "obab2"
                }
            }
            """;

        mockMvc.perform(post("/duplication/nickname")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
            .andExpect(status().isOk());
    }

    @Test
    void 간단유저정보조회API_성공() throws Exception {
        String userId = "user123";

        UserReaderCommand dummyUser = UserReaderCommand.builder()
            .userId(userId)
            .nickName("오밥이")
            .build();

        when(userReaderUseCase.getUserInfoWithUnregisteredNickname(userId)).thenReturn(dummyUser);

        mockMvc.perform(get("/simple-info")
                .param("userId", userId))
            .andExpect(status().isOk());
    }

}
