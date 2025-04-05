package token.domain.command;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import token.adapter.input.web.request.FcmTokenSavingRequest;
import token.adapter.output.persistence.enums.DeviceType;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FcmTokenSaveCommand {

    private String token;

    private String userId;

    private DeviceType deviceType;

    private LocalDate savedAt;

    public static FcmTokenSaveCommand of(FcmTokenSavingRequest request, String userId) {
        return FcmTokenSaveCommand.builder()
                .token(request.getToken())
                .userId(userId)
                .deviceType(request.getDeviceType())
                .savedAt(LocalDate.now())
                .build();
    }

}
