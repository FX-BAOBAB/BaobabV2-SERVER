package token.adapter.output.persistence;

import global.annotation.output.PersistenceAdapter;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
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
    public void deleteTokensUpTo(LocalDate threshold) {
        fcmTokenMongoRepository.deleteBySavedAtLessThanEqual(threshold);
    }

}
