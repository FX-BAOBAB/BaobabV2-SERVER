package token.adapter.output.persistence.repository.fcmToken;

import java.time.LocalDate;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import token.adapter.output.persistence.enums.DeviceType;

public interface FcmTokenMongoRepository extends MongoRepository<FcmToken, String> {

    void deleteByDeviceTypeAndSavedAtLessThanEqual(DeviceType deviceType, LocalDate threshold);

    List<FcmToken> findByUserIdIn(List<String> userIds);

}
