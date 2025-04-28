package message.domain.dto;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import message.adapter.output.persistence.enums.DeviceType;
import message.domain.command.FcmTokenSaveCommand;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FcmTokenSaveForm {

    private String token;

    private String userId;

    private DeviceType deviceType;

    private LocalDate savedAt;

    public static FcmTokenSaveForm of(FcmTokenSaveCommand command) {
        return FcmTokenSaveForm.builder()
            .token(command.getToken())
            .userId(command.getUserId())
            .deviceType(command.getDeviceType())
            .savedAt(command.getSavedAt())
            .build();
    }

}
