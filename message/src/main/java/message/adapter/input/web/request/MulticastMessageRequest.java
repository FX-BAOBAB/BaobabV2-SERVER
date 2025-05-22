package message.adapter.input.web.request;

import jakarta.validation.constraints.*;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MulticastMessageRequest {

    @NotNull(message = "null 값일 수 없습니다.")
    private String title;

    @NotNull(message = "null 값일 수 없습니다.")
    private String body;

    @NotEmpty(message = "null 값이거나 비어 있을 수 없습니다.")
    private List<String> userIds;

}
