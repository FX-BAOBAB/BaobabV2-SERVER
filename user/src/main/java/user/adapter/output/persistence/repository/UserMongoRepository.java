package user.adapter.output.persistence.repository;

import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;
import user.adapter.output.persistence.enums.UserStatus;

public interface UserMongoRepository extends MongoRepository<User, String> {

    boolean existsByAccount_Email(String email);

    boolean existsByNickName(String nickName);

    Optional<User> findFirstByIdAndStatusOrderByIdDesc(String userId, UserStatus status);

    Optional<User> findFirstByAccount_EmailAndStatusOrderByIdDesc(String email, UserStatus status);

}
