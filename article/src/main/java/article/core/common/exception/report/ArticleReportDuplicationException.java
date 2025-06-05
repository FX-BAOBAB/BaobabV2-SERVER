package article.core.common.exception.report;

import global.errorcode.ErrorCodeIfs;
import lombok.Getter;

@Getter
public class ArticleReportDuplicationException extends RuntimeException {

    private final ErrorCodeIfs errorCodeIfs;
    private final String description;


    public ArticleReportDuplicationException(ErrorCodeIfs errorCodeIfs) {
        super(errorCodeIfs.getDescription());
        this.errorCodeIfs = errorCodeIfs;
        this.description = errorCodeIfs.getDescription();
    }

    public ArticleReportDuplicationException(ErrorCodeIfs errorCodeIfs, String errorDescription) {
        this.errorCodeIfs = errorCodeIfs;
        this.description = errorDescription;
    }

    public ArticleReportDuplicationException(ErrorCodeIfs errorCodeIfs, Throwable throwable) {
        super(throwable);
        this.errorCodeIfs = errorCodeIfs;
        this.description = errorCodeIfs.getDescription();
    }

    public ArticleReportDuplicationException(ErrorCodeIfs errorCodeIfs, Throwable throwable, String errorDescription) {
        super(throwable);
        this.errorCodeIfs = errorCodeIfs;
        this.description = errorDescription;
    }

}
