package cn.ff.dionysus.common.basal.exception;

import cn.ff.dionysus.common.basal.entity.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.validation.BindException;
import java.util.List;

/**
 * 全局异常捕获类
 *
 * @author fengfan 2019/12/31
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 全局异常.
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public R exception(Exception e) {
        log.error("全局异常信息 ex={}", e.getMessage(), e);
        return R.fail(e.getMessage());
    }

    /**
     * validation Exception
     */
    @ExceptionHandler({MethodArgumentNotValidException.class, BindException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public R bodyValidExceptionHandler(MethodArgumentNotValidException exception) {
        List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();
        log.warn(fieldErrors.get(0).getDefaultMessage());
        return R.fail(fieldErrors.get(0).getDefaultMessage());
    }
}
