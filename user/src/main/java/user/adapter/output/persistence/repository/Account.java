package user.adapter.output.persistence.repository;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import user.domain.command.AccountCommand;
import user.domain.command.UserUpdateCommand;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Account {

    private String email;

    private String password;

    private String name;

    public Account updateAccountInfo(AccountCommand accountCommand) {
        this.setPassword(accountCommand.getPassword());
        this.setName(accountCommand.getName());
        return this;
    }

}
