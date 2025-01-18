package user.application.port.input;

public interface TokenValidationUseCase {

    String validateToken(String accessToken);

}
