package user.adapter.output.persistence.repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import user.adapter.output.persistence.enums.UserRole;
import user.adapter.output.persistence.enums.UserStatus;
import user.domain.command.UserUpdateCommand;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "user")
public class User {

    @Id
    private String id;

    private String nickName;

    private String phone;

    private String department;

    private LocalDate birth;

    private String imageId;

    private UserRole role;

    private UserStatus status;

    private LocalDateTime registeredAt;

    private LocalDateTime unregisteredAt;

    private LocalDateTime lastLoginAt;

    private Account account;

    private Address address;

    public User updateUserInfo(UserUpdateCommand userUpdateCommand) {
        this.nickName = userUpdateCommand.getNickName();
        this.phone = userUpdateCommand.getPhone();
        this.department = userUpdateCommand.getDepartment();
        this.birth = userUpdateCommand.getBirth();
        this.imageId = userUpdateCommand.getImageId();
        this.account.updateAccountInfo(userUpdateCommand.getAccountCommand());
        this.address.updateAddressInfo(userUpdateCommand.getAddressCommand());
        return this;
    }


}
