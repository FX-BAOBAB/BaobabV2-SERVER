package user.application.port.output;

import user.adapter.output.persistence.Account;

public interface AccountRegisterPort {

    boolean save(Account account);


}
