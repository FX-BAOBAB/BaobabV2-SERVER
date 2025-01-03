package user.adapter.output;

import global.annotation.PersistenceAdapter;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import user.adapter.output.persistence.Account;
import user.adapter.output.persistence.AccountMongoRepository;
import user.application.port.output.AccountReaderPort;
import user.application.port.output.AccountRegisterPort;
import user.domain.command.AccountRegisterCommand;

@PersistenceAdapter
@RequiredArgsConstructor
public class AccountPersistenceAdapter implements AccountRegisterPort, AccountReaderPort {

    private final AccountMongoRepository accountMongoRepository;

    @Override
    public boolean save(AccountRegisterCommand accountRegisterCommand) {
        Account account = Account.builder()
            .userId(accountRegisterCommand.getUserId())
            .email(accountRegisterCommand.getEmail())
            .password(accountRegisterCommand.getPassword())
            .nickName(accountRegisterCommand.getNickName())
            .build();
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

    @Override
    public Optional<Account> findFirstByUserId(String userId) {
        return accountMongoRepository.findFirstByUserId(userId);
    }

}
