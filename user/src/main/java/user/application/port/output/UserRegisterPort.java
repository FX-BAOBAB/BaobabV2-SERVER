package user.application.port.output;

import user.adapter.output.persistence.User;

public interface UserRegisterPort {

    boolean save(User user);

}
