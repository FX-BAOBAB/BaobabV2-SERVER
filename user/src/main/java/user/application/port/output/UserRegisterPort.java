package user.application.port.output;

import user.adapter.output.persistence.User;

public interface UserRegisterPort {

    User save(User user);

}
