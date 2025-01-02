package user.adapter.output;

import global.annotation.PersistenceAdapter;
import lombok.RequiredArgsConstructor;
import user.adapter.output.persistence.Account;
import user.adapter.output.persistence.AccountMongoRepository;
import user.application.port.output.AccountReaderPort;
import user.application.port.output.AccountRegisterPort;

@PersistenceAdapter
@RequiredArgsConstructor
public class AccountPersistenceAdapter implements AccountRegisterPort, AccountReaderPort {

    private final AccountMongoRepository accountMongoRepository;

    @Override
    public boolean save(Account account) {
        accountMongoRepository.save(account);
        return true;
    }

    @Override
    public boolean checkEmailDuplicate(String email) {
        return accountMongoRepository.existsByEmail(email);
    }

    @Override
    public boolean checkNickNameDuplicate(String nickName) {
        return accountMongoRepository.existsByNickName(nickName);
    }
}
