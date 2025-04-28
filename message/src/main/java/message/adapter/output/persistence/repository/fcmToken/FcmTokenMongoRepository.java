package message.adapter.output.persistence.repository.fcmToken;

import java.time.LocalDate;
import java.util.List;
import message.adapter.output.persistence.enums.DeviceType;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface FcmTokenMongoRepository extends MongoRepository<FcmToken, String> {

    void deleteByDeviceTypeAndSavedAtLessThanEqual(DeviceType deviceType, LocalDate threshold);

    List<FcmToken> findByUserIdIn(List<String> userIds);

}
