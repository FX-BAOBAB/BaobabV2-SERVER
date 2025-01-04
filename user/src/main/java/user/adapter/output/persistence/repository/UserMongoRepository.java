package user.adapter.output.persistence.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserMongoRepository extends MongoRepository<User, String> {

    boolean existsByEmail(String email);

    boolean existsByNickName(String nickName);

}
