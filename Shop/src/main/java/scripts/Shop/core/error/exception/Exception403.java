package scripts.Shop.core.error.exception;


import org.springframework.http.HttpStatus;
import scripts.Shop.core.utils.ApiUtils;


public class Exception403 extends RuntimeException{

    public Exception403(String message) {
        super(message);
    }

    public ApiUtils.ApiResult<?> body(){
        return ApiUtils.error(getMessage(), HttpStatus.FORBIDDEN);
    }

    public HttpStatus status() {
        return HttpStatus.FORBIDDEN;
    }
}
