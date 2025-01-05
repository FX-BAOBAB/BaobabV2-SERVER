package user.adapter.output.persistence.repository;

import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;
import user.adapter.output.persistence.enums.UserStatus;

public interface UserMongoRepository extends MongoRepository<User, String> {

    boolean existsByEmail(String email);

    boolean existsByNickName(String nickName);

    Optional<User> findFirstByIdAndStatusOrderByIdDesc(String userId, UserStatus status);

    Optional<User> findFirstByEmailAndStatusOrderByIdDesc(String email, UserStatus status);

}
