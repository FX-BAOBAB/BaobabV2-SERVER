package message.adapter.input.web.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SubscriptionResponse {

    private String topic;

    private boolean isSuccess;

    public static SubscriptionResponse of(String topic, boolean isSuccess) {
        return SubscriptionResponse.builder()
            .topic(topic)
            .isSuccess(isSuccess)
            .build();
    }

}
