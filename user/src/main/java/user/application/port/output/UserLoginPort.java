package user.application.port.output;


import user.adapter.output.persistence.User;

public interface UserLoginPort {

    User findByUserId(String userId);

}
