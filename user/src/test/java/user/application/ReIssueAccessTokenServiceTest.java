package user.application;

import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import user.security.jwt.model.TokenDto;
import user.security.jwt.service.TokenIssueService;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReIssueAccessTokenServiceTest {

    @Mock private TokenIssueService tokenIssueService;

    @InjectMocks private ReIssueAccessTokenService reIssueAccessTokenService;

    @Test
    void AccessToken_재발급_성공() {

        // Given
        String refreshToken = "refreshToken";
        TokenDto reIssueTokenDto = TokenDto.builder()
            .token("new-access-token")
            .expiredAt(LocalDateTime.now().plusHours(1))
            .build();

        when(tokenIssueService.reIssueAccessToken(refreshToken)).thenReturn(reIssueTokenDto);

        // When
        TokenDto result = reIssueAccessTokenService.reIssueAccessToken(refreshToken);

        assertThat(result).isNotNull();
        assertThat(result.getToken()).isEqualTo("new-access-token");
        assertThat(result.getExpiredAt()).isAfter(LocalDateTime.now());
    }

}
