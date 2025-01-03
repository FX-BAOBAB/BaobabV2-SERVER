package user.application.port.input;

import user.domain.command.UserReaderCommand;

public interface UserReaderUseCase {

    UserReaderCommand getUserInfoBy(String userId);

}
