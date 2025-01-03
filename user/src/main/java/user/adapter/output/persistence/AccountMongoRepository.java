package user.adapter.output.persistence;

import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AccountMongoRepository extends MongoRepository<Account, String> {

    boolean existsByEmail(String email);

    boolean existsByNickName(String nickName);

    Optional<Account> findFirstByUserId(String userId);

}