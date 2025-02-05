package user.adapter.output.persistence.repository;

import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;
import user.adapter.output.persistence.enums.UserStatus;

public interface UserMongoRepository extends MongoRepository<UserDocument, String> {

    boolean existsByUserAccount_Email(String email);

    boolean existsByNickName(String nickName);

    Optional<UserDocument> findFirstByIdAndStatusOrderByIdDesc(String userId, UserStatus status);

    Optional<UserDocument> findFirstByUserAccount_EmailAndStatusOrderByIdDesc(String email, UserStatus status);

}
