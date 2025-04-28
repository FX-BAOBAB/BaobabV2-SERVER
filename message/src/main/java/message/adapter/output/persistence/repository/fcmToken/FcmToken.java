package message.adapter.output.persistence.repository.fcmToken;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import message.adapter.output.persistence.enums.DeviceType;
import message.domain.dto.FcmTokenSaveForm;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "fcm_token")
public class FcmToken {

    @Id
    private String token;

    private String userId;

    private DeviceType deviceType;

    private LocalDate savedAt;

    public static FcmToken of(FcmTokenSaveForm fcmTokenSaveForm) {
        return FcmToken.builder()
            .token(fcmTokenSaveForm.getToken())
            .userId(fcmTokenSaveForm.getUserId())
            .deviceType(fcmTokenSaveForm.getDeviceType())
            .savedAt(fcmTokenSaveForm.getSavedAt())
            .build();
    }

}
