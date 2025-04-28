package message.adapter.output.persistence;

import global.annotation.output.PersistenceAdapter;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import message.adapter.output.persistence.enums.DeviceType;
import message.adapter.output.persistence.repository.fcmToken.FcmToken;
import message.adapter.output.persistence.repository.fcmToken.FcmTokenMongoRepository;
import message.application.port.output.FcmTokenPersistencePort;
import message.domain.dto.FcmTokenSaveForm;

@PersistenceAdapter
@RequiredArgsConstructor
public class FcmTokenPersistenceAdapter implements FcmTokenPersistencePort {

    private final FcmTokenMongoRepository fcmTokenMongoRepository;

    @Override
    public boolean saveFcmToken(FcmTokenSaveForm fcmTokenSaveForm) {
        FcmToken savedFcmToken = fcmTokenMongoRepository.save(FcmToken.of(fcmTokenSaveForm));
        return savedFcmToken.getToken() != null;
    }

    @Override
    public List<String> findByUserIds(List<String> userIds) {
        return fcmTokenMongoRepository.findByUserIdIn(userIds).stream()
            .map(FcmToken::getToken)
            .toList();
    }

    @Override
    public boolean deleteFcmToken(String token) {
        fcmTokenMongoRepository.deleteById(token);
        Optional<FcmToken> article = fcmTokenMongoRepository.findById(token);
        return article.isEmpty();
    }

    @Override
    public void deleteTokensUpTo(DeviceType deviceType, LocalDate threshold) {
        fcmTokenMongoRepository.deleteByDeviceTypeAndSavedAtLessThanEqual(deviceType, threshold);
    }

}
