package user.adapter.input.web;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import global.api.Api;
import java.time.LocalDate;
import java.time.LocalDateTime;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import user.adapter.input.web.request.DuplicationEmailRequest;
import user.adapter.input.web.request.DuplicationNickNameRequest;
import user.adapter.input.web.request.UserLoginRequest;
import user.adapter.input.web.request.UserRegisterRequest;
import user.adapter.input.web.response.TokenResponse;
import user.adapter.output.persistence.repository.UserMongoRepository;

@AutoConfigureMockMvc
@SpringBootTest
@Import({TestJacksonConfig.class})
class UserOpenApiControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserMongoRepository userMongoRepository;

    @Autowired
    private MockMvc mockMvc;

    @AfterEach
    void tearDown() {
        userMongoRepository.deleteByAccount_Email("test@example.com");
    }

    @Test
    @DisplayName("회원가입 - 성공")
    void registerUser_success() throws Exception {

        // Given
        UserRegisterRequest request = getUserRegisterRequest();

        Api<UserRegisterRequest> apiRequest = new Api<>();
        apiRequest.setBody(request);

        // When
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/open-api/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(apiRequest)))
            .andExpect(status().isOk())
            .andReturn();

        // Then
        String responseContent = result.getResponse().getContentAsString();
        Api response = new ObjectMapper().readValue(responseContent, Api.class);
        String userId = (String) response.getBody();

        assertNotNull(userId, "회원가입 후 userId 는 null 이 아니어야 합니다.");

    }

    @Test
    @DisplayName("로그인 - 성공")
    void login_success() throws Exception {

        // Given
        registerUser();

        UserLoginRequest userLoginRequest = new UserLoginRequest();
        userLoginRequest.setEmail("test@example.com");
        userLoginRequest.setPassword("Password@123");

        Api<UserLoginRequest> apiRequest = new Api<>();
        apiRequest.setBody(userLoginRequest);

        // When
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/open-api/user/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(apiRequest)))
            .andExpect(status().isOk())
            .andReturn();

        // Then
        String responseContent = result.getResponse().getContentAsString();
        Api<TokenResponse> response = objectMapper.readValue(responseContent, new TypeReference<>() {});
        TokenResponse tokenResponse = response.getBody();

        assertThat(tokenResponse).isNotNull();
        assertThat(tokenResponse.getAccessToken()).isNotEmpty();
        assertThat(tokenResponse.getRefreshToken()).isNotEmpty();
        assertThat(tokenResponse.getAccessTokenExpiredAt()).isAfter(LocalDateTime.now());
        assertThat(tokenResponse.getRefreshTokenExpiredAt()).isAfter(tokenResponse.getAccessTokenExpiredAt());
    }

    @Test
    @DisplayName("닉네임 중복 확인 - 성공")
    void checkDuplicateNickName_success() throws Exception {

        // Given
        registerUser(); // testUser 닉네임 등록

        DuplicationNickNameRequest duplicationNickNameRequest = new DuplicationNickNameRequest();
        duplicationNickNameRequest.setNickName("testUser1");

        Api<DuplicationNickNameRequest> apiRequest = new Api<>();
        apiRequest.setBody(duplicationNickNameRequest);

        // When
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/open-api/user/duplication/nickname")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(apiRequest)))
            .andExpect(status().isOk())
            .andReturn();

        String responseContent = result.getResponse().getContentAsString();
        Api<Boolean> response = objectMapper.readValue(responseContent, new TypeReference<>() {});
        Boolean dupleResponse = response.getBody();

        assertTrue(dupleResponse, "닉네임이 중복되지 않습니다.");

    }

    @Test
    @DisplayName("닉네임 중복 확인 - 실패")
    void checkDuplicateNickName_fail() throws Exception {

        // Given
        registerUser(); // testUser 닉네임 등록

        DuplicationNickNameRequest duplicationNickNameRequest = new DuplicationNickNameRequest();
        duplicationNickNameRequest.setNickName("testUser");

        Api<DuplicationNickNameRequest> apiRequest = new Api<>();
        apiRequest.setBody(duplicationNickNameRequest);

        // When
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/open-api/user/duplication/nickname")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(apiRequest)))
            .andExpect(status().isConflict())
            .andReturn();

        String responseContent = result.getResponse().getContentAsString();
        Api<Boolean> response = objectMapper.readValue(responseContent, new TypeReference<>() {});
        Boolean dupleResponse = response.getBody();

        assertNull(dupleResponse, "닉네임이 중복됩니다.");

    }

    @Test
    @DisplayName("이메일 중복 확인 - 성공")
    void checkDuplicateEmail_success() throws Exception {

        // Given
        registerUser(); // test@example.com 이메일 등록

        DuplicationEmailRequest duplicationEmailRequest = new DuplicationEmailRequest();
        duplicationEmailRequest.setEmail("test1@example.com");

        Api<DuplicationEmailRequest> apiRequest = new Api<>();
        apiRequest.setBody(duplicationEmailRequest);

        // When
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/open-api/user/duplication/email")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(apiRequest)))
            .andExpect(status().isOk())
            .andReturn();

        String responseContent = result.getResponse().getContentAsString();
        Api<Boolean> response = objectMapper.readValue(responseContent, new TypeReference<>() {});
        Boolean dupleResponse = response.getBody();

        assertTrue(dupleResponse, "이메일이 중복되지 않습니다.");

    }

    @Test
    @DisplayName("이메일 중복 확인 - 실패")
    void checkDuplicateEmail_fail() throws Exception {

        // Given
        registerUser(); // test@example.com 이메일 등록

        DuplicationEmailRequest duplicationEmailRequest = new DuplicationEmailRequest();
        duplicationEmailRequest.setEmail("test@example.com");

        Api<DuplicationEmailRequest> apiRequest = new Api<>();
        apiRequest.setBody(duplicationEmailRequest);

        // When
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/open-api/user/duplication/email")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(apiRequest)))
            .andExpect(status().isConflict())
            .andReturn();

        String responseContent = result.getResponse().getContentAsString();
        Api<Boolean> response = objectMapper.readValue(responseContent, new TypeReference<>() {});
        Boolean dupleResponse = response.getBody();

        assertNull(dupleResponse, "이메일이 중복됩니다.");

    }

/*    @Test
    @DisplayName("토큰 재발급 - 성공")
    void reIssueAccessToken_success() throws Exception {
        // Given
        TokenResponse loginResponse = loginUser();
        String refreshToken = loginResponse.getRefreshToken();

        // When
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/open-api/user/reissue")
                .header("Authorization", "Bearer " + refreshToken)
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andReturn();

        // Then
        String responseContent = result.getResponse().getContentAsString();
        Api<TokenDto> response = objectMapper.readValue(responseContent, new TypeReference<>() {});
        TokenDto tokenDto = response.getBody();

        assertThat(tokenDto).isNotNull();
        assertThat(tokenDto.getToken()).isNotEmpty();
        assertThat(tokenDto.getExpiredAt()).isAfter(LocalDateTime.now());
    }*/

/*
    @Test
    @DisplayName("프로필 이미지 등록 - 성공")
    void registerImage_success() throws Exception {

        // Given
        String userId = registerUserAndGetId();

        MockMultipartFile profileImage = new MockMultipartFile(
            "profileImage",
            "test-image.jpg",
            MediaType.IMAGE_JPEG_VALUE,
            "Test image content".getBytes()
        );

        // When
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.multipart("/open-api/user/image")
                .file(profileImage)
                .param("userId", userId)
                .contentType(MediaType.MULTIPART_FORM_DATA))
            .andExpect(status().isOk())
            .andReturn();

        // Then
        String responseContent = result.getResponse().getContentAsString();
        Api<?> response = objectMapper.readValue(responseContent, Api.class);
        Boolean result = (Boolean) response.getBody();

        assertTrue(result, "프로필 이미지 업로드 후 True 값이어야 합니다.");
    }
*/

    private static UserRegisterRequest getUserRegisterRequest() {
        UserRegisterRequest request = new UserRegisterRequest();
        request.setEmail("test@example.com");
        request.setPassword("Password@123");
        request.setNickName("testUser");
        request.setName("testName");
        request.setPhone("010-1234-5678");
        request.setDepartment("IT");
        request.setBirth(LocalDate.of(2000, 1, 1));
        request.setAddress("서울시 서초구");
        request.setDetailAddress("양재동 123-45");
        request.setBasicAddress("서울");
        request.setPost("12345");
        return request;
    }

    private String registerUser() throws Exception {
        UserRegisterRequest request = getUserRegisterRequest();
        Api<UserRegisterRequest> apiRequest = new Api<>();
        apiRequest.setBody(request);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/open-api/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(apiRequest)))
            .andExpect(status().isOk())
            .andReturn();

        String responseContent = result.getResponse().getContentAsString();
        Api<?> response = objectMapper.readValue(responseContent, Api.class);
        return (String) response.getBody();
    }

    private TokenResponse loginUser() throws Exception {
        registerUser();

        UserLoginRequest userLoginRequest = new UserLoginRequest();
        userLoginRequest.setEmail("test@example.com");
        userLoginRequest.setPassword("Password@123");

        Api<UserLoginRequest> apiRequest = new Api<>();
        apiRequest.setBody(userLoginRequest);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/open-api/user/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(apiRequest)))
            .andExpect(status().isOk())
            .andReturn();

        String responseContent = result.getResponse().getContentAsString();
        Api<TokenResponse> response = objectMapper.readValue(responseContent, new TypeReference<>() {});
        return response.getBody();
    }

}