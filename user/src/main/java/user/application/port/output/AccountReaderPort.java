package user.application.port.output;

public interface AccountReaderPort {

    boolean checkEmailDuplicate(String email);

    boolean checkNickNameDuplicate(String name);

}
