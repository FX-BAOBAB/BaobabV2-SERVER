package file.core.common.error;

import global.errorcode.ErrorCodeIfs;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ImageErrorCode implements ErrorCodeIfs {

    IMAGE_UPLOAD_ERROR(HttpStatus.INTERNAL_SERVER_ERROR.value(), 1300, "이미지를 저장할 수 없습니다."),
    IMAGE_DELETE_ERROR(HttpStatus.INTERNAL_SERVER_ERROR.value(), 1301, "이미지를 삭제할 수 없습니다."),
    NULL_POINT(HttpStatus.INTERNAL_SERVER_ERROR.value(), 1302, "NULL POINT 입니다."),
    IMAGE_NOT_FOUND(HttpStatus.NOT_FOUND.value(), 1303, "요청하신 이미지를 찾을 수 없습니다."),
    IMAGE_DIRECTORY_ERROR(HttpStatus.INTERNAL_SERVER_ERROR.value(), 1304, "파일이 저장될 디렉터리를 생성할 수 없습니다.")
    ;

    private final Integer httpCode;
    private final Integer errorCode;
    private final String description;

}