package token.adapter.output.persistence;

import global.annotation.output.PersistenceAdapter;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import token.adapter.output.persistence.repository.fcmToken.FcmToken;
import token.adapter.output.persistence.repository.fcmToken.FcmTokenMongoRepository;
import token.application.port.output.FcmTokenPersistencePort;
import token.domain.dto.FcmTokenSaveForm;

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
    public void deleteTokensUpTo(LocalDate threshold) {

    }

    @Override
    public String findByUserId(String userId) {
        return null;
    }

    @Override
    public List<String> findByUserIds(List<String> userIds) {
        return null;
    }

}
