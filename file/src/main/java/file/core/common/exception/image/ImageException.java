package file.core.common.exception.image;

import global.errorcode.ErrorCodeIfs;

public interface ImageException {
    ErrorCodeIfs getErrorCodeIfs();
    String getDescription();
}
