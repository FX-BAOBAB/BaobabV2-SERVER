package token.adapter.output.persistence.repository.fcmToken;

import java.time.LocalDate;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface FcmTokenMongoRepository extends MongoRepository<FcmToken, String> {

    void deleteBySavedAtLessThanEqual(LocalDate threshold);

    FcmToken findByUserId(String userId);

    List<FcmToken> findByUserIdIn(List<String> userIds);

}
