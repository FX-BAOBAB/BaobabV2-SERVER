package user.core.common.exception.user;

import global.errorcode.ErrorCodeIfs;
import lombok.Getter;

@Getter
public class EmailVerificationExpiredException extends RuntimeException {

    private final ErrorCodeIfs errorCodeIfs;
    private final String description;

    public EmailVerificationExpiredException(ErrorCodeIfs errorCodeIfs) {
        super(errorCodeIfs.getDescription());
        this.errorCodeIfs = errorCodeIfs;
        this.description = errorCodeIfs.getDescription();
    }

    public EmailVerificationExpiredException(ErrorCodeIfs errorCodeIfs, String errorDescription) {
        this.errorCodeIfs = errorCodeIfs;
        this.description = errorDescription;
    }

    public EmailVerificationExpiredException(ErrorCodeIfs errorCodeIfs, Throwable throwable) {
        super(throwable);
        this.errorCodeIfs = errorCodeIfs;
        this.description = errorCodeIfs.getDescription();
    }

    public EmailVerificationExpiredException(ErrorCodeIfs errorCodeIfs, Throwable throwable,
        String errorDescription) {
        super(throwable);
        this.errorCodeIfs = errorCodeIfs;
        this.description = errorDescription;
    }

}
