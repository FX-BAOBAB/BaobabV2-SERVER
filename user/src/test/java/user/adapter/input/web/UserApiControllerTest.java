package user.adapter.input.web;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import file.application.port.input.DefaultImageUseCase;
import global.resolver.AuthUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import user.application.port.input.UserReaderUseCase;
import user.application.port.input.UserUnRegisterUseCase;
import user.application.port.input.UserUpdateUseCase;
import user.domain.command.UserReaderCommand;

@ExtendWith(MockitoExtension.class)
class UserApiControllerTest {

    @InjectMocks
    private UserApiController userApiController;

    @Mock
    private UserUpdateUseCase userUpdateUseCase;
    @Mock
    private UserUnRegisterUseCase userUnRegisterUseCase;
    @Mock
    private UserReaderUseCase userReaderUseCase;
    @Mock
    private DefaultImageUseCase defaultImageUseCase;

    private MockMvc mockMvc;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(userApiController).build();
    }

    @Test
    void 업데이트API_성공() throws Exception {

        MockMultipartFile profileImage = new MockMultipartFile(
            "profileImage",
            "profile.png",
            "image/png",
            "dummy-image-content".getBytes()
        );

        mockMvc.perform(multipart("/update")
                .file(profileImage)
                .param("nickName", "오밥이")
                .param("carrierType", "SKT")
                .param("phoneNumber", "010-1234-5678")
                .param("address", "서울 강남구")
                .param("detailAddress", "101동 202호")
                .param("basicAddress", "true")
                .param("post", "12345")
                .param("password", "Password1")
                .contentType(MediaType.MULTIPART_FORM_DATA))
            .andExpect(status().isOk());
    }

    @Test
    void 회원탈퇴API_성공() throws Exception {
        String requestJson = """
                {
                    "body": {
                        "password": "Password1"
                    }
                }
            """;

        mockMvc.perform(post("/unregister")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
            .andExpect(status().isOk());
    }

    @Test
    void 내정보조회API_성공() throws Exception {
        String userId = "userId123";

        UserReaderCommand userReaderCommand = UserReaderCommand.builder()
            .userId(userId)
            .nickName("오밥이")
            .build();

        when(userReaderUseCase.getUserInfoBy(userId)).thenReturn(userReaderCommand);

        mockMvc = MockMvcBuilders.standaloneSetup(userApiController)
            .setCustomArgumentResolvers(new HandlerMethodArgumentResolver() {
                @Override
                public boolean supportsParameter(MethodParameter parameter) {
                    return parameter.getParameterType().equals(AuthUser.class);
                }

                @Override
                public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                    NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
                    return AuthUser.builder().userId(userId).build();
                }
            })
            .build();

        mockMvc.perform(get("/info")
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
    }

    @Test
    void 기본이미지저장API_성공() throws Exception {

        MockMultipartFile defaultProfileImage = new MockMultipartFile(
            "defaultImage",
            "defaultImage.png",
            "image/png",
            "dummy-image-content".getBytes()
        );

        mockMvc.perform(multipart("/default-image")
                .file(defaultProfileImage)
                .contentType(MediaType.MULTIPART_FORM_DATA))
            .andExpect(status().isOk());

    }

    @Test
    void 기본이미지조회API_성공() throws Exception {
        mockMvc.perform(get("/default-image")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
    }

}
