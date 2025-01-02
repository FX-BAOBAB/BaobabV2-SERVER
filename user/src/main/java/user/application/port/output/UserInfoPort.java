package user.application.port.output;

import user.adapter.output.persistence.User;

public interface UserInfoPort {

    User findByUserId(String userId);

}
