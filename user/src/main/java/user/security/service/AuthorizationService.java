package user.security.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import user.adapter.output.persistence.enums.UserStatus;
import user.application.port.output.UserPersistencePort;
import user.domain.command.UserReaderCommand;
import user.domain.dto.UserDto;

@Service
@RequiredArgsConstructor
public class AuthorizationService  implements UserDetailsService {

    private final UserPersistencePort userPersistencePort;

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {

        UserDto userDto = userPersistencePort.getUserInfoBy(userId,
            UserStatus.REGISTERED);

        return User.builder()
            .username(userDto.getEmail())
            .password(userDto.getPassword())
            .roles(userDto.getRole().toString())
            .build();
    }

}
