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
public class UserUpdateCommand {

    private String userId;

    private String nickName;

    private String phone;

    private LocalDate birth;

    private String department;

    private String imageId;

    private AccountCommand accountCommand;

    private AddressCommand addressCommand;

}