package user.application.port.output;

import java.util.Optional;
import user.adapter.output.persistence.Account;

public interface AccountReaderPort {

    boolean checkEmailDuplicate(String email);

    boolean checkNickNameDuplicate(String name);

    Optional<Account> findFirstByUserId(String userId);

}
