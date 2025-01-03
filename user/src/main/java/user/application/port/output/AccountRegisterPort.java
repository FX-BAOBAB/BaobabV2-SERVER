package user.application.port.output;

import user.domain.command.AccountRegisterCommand;

public interface AccountRegisterPort {

    boolean save(AccountRegisterCommand accountRegisterCommand);

}
