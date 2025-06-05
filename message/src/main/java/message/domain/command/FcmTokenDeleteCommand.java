package message.domain.command;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import message.adapter.input.web.request.FcmTokenDeletionRequest;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FcmTokenDeleteCommand {

    private String token;

    private String userId;

    public static FcmTokenDeleteCommand of(FcmTokenDeletionRequest request, String userId) {
        return FcmTokenDeleteCommand.builder()
                .token(request.getToken())
                .userId(userId)
                .build();
    }

}
