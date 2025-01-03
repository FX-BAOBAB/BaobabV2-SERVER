package user.application.port.output;

import user.adapter.output.persistence.User;
import user.adapter.output.persistence.enums.UserStatus;

public interface UserReaderPort {

    User findByUserIdAndStatus(String userId, UserStatus userStatus);

}
