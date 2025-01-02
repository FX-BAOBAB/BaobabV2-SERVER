package user.application.port.output;

import user.adapter.output.persistence.User;

public interface UserUpdatePort {

    boolean update(User user);

}
