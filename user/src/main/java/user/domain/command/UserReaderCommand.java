package user.domain.command;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserReaderCommand {

    private String userId;

    private String email;

    private String name;

    private String nickName;

    private LocalDate birth;

    private String department;

    private String address;

    private String detailAddress;

    private String basicAddress;

    private String post;

    private Long imageId;

}