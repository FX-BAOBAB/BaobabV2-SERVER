package message.adapter.output.persistence.repository.fcmToken;

import com.mongodb.client.result.DeleteResult;
import java.time.LocalDate;
import java.util.List;
import message.adapter.output.persistence.enums.DeviceType;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface FcmTokenMongoRepository extends MongoRepository<FcmToken, String> {

    @Query(value = "{ 'deviceType': ?0, 'savedAt': { '$lte': ?1 } }", delete = true)
    long deleteByDeviceTypeAndSavedAtLessThanEqual(DeviceType deviceType, LocalDate threshold);

    List<FcmToken> findByUserIdIn(List<String> userIds);

}
