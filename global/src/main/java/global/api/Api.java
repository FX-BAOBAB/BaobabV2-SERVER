package global.api;

import global.errorcode.ErrorCodeIfs;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Api<T> {

    private Result result;

    private T body;

    public static <T> Api<T> OK(T object){
        Api<T> api = new Api<T>();
        api.body = object;
        api.result = Result.OK();
        return api;
    }

    public static Api<Object> ERROR(Result result){
        Api<Object> api = new Api<Object>();
        api.result = result;
        return api;
    }

    public static Api<Object> ERROR(ErrorCodeIfs errorCodeIfs){
        Api<Object> api = new Api<Object>();
        api.result = Result.ERROR(errorCodeIfs);
        return api;
    }

    public static Api<Object> ERROR(ErrorCodeIfs errorCodeIfs,Throwable tx){
        Api<Object> api = new Api<Object>();
        api.result = Result.ERROR(errorCodeIfs,tx);
        return api;
    }

    public static Api<Object> ERROR(ErrorCodeIfs errorCodeIfs,String description){
        Api<Object> api = new Api<Object>();
        api.result = Result.ERROR(errorCodeIfs,description);
        return api;
    }

}