package user.security.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import user.adapter.output.persistence.enums.UserStatus;
import user.adapter.output.persistence.repository.UserDocument;
import user.application.port.output.UserPersistencePort;
import user.domain.command.UserReaderCommand;

@Service
@RequiredArgsConstructor
public class AuthorizationService implements UserDetailsService {

    private final UserPersistencePort userPersistencePort;

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {

        UserDocument userDocument = userPersistencePort.getUserDocument(userId,
            UserStatus.REGISTERED);

        return User.builder()
            .username(userDocument.getAccount().getEmail())
            .password(userDocument.getAccount().getPassword())
            .roles(userDocument.getRole().toString())
            .build();
    }

}
