package user.adapter.output.persistence.repository;

import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;
import user.adapter.output.persistence.enums.UserStatus;

public interface UserMongoRepository extends MongoRepository<UserDocument, String> {

    boolean existsByAccount_Email(String email);

    boolean existsByNickName(String nickName);

    Optional<UserDocument> findFirstByIdAndStatusOrderByIdDesc(String userId, UserStatus status);

    Optional<UserDocument> findFirstByAccount_EmailAndStatusOrderByIdDesc(String email, UserStatus status);

}
