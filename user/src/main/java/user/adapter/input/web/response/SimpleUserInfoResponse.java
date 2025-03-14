package user.adapter.input.web.response;

import lombok.Builder;
import lombok.Data;
import user.domain.command.UserReaderCommand;
import user.domain.dto.ProfileImage;

@Data
@Builder
public class SimpleUserInfoResponse {

    private String nickname;

    private String profileImageUrl;

    public static SimpleUserInfoResponse toResponse(UserReaderCommand userReaderCommand) {
        return SimpleUserInfoResponse.builder()
            .nickname(userReaderCommand.getNickName())
            .profileImageUrl(userReaderCommand.getProfileImage() != null ? userReaderCommand.getProfileImage().getImageUrl() : null)
            .build();
    }

}
