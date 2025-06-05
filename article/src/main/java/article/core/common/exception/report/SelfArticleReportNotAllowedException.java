package article.core.common.exception.report;

import global.errorcode.ErrorCodeIfs;
import lombok.Getter;

@Getter
public class SelfArticleReportNotAllowedException extends RuntimeException {

    private final ErrorCodeIfs errorCodeIfs;
    private final String description;


    public SelfArticleReportNotAllowedException(ErrorCodeIfs errorCodeIfs) {
        super(errorCodeIfs.getDescription());
        this.errorCodeIfs = errorCodeIfs;
        this.description = errorCodeIfs.getDescription();
    }

    public SelfArticleReportNotAllowedException(ErrorCodeIfs errorCodeIfs, String errorDescription) {
        this.errorCodeIfs = errorCodeIfs;
        this.description = errorDescription;
    }

    public SelfArticleReportNotAllowedException(ErrorCodeIfs errorCodeIfs, Throwable throwable) {
        super(throwable);
        this.errorCodeIfs = errorCodeIfs;
        this.description = errorCodeIfs.getDescription();
    }

    public SelfArticleReportNotAllowedException(ErrorCodeIfs errorCodeIfs, Throwable throwable, String errorDescription) {
        super(throwable);
        this.errorCodeIfs = errorCodeIfs;
        this.description = errorDescription;
    }

}
