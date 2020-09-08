package com.myzy.patient.core.exception;

import com.baomidou.mybatisplus.extension.api.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author leekejin
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ResponseBody
    @ExceptionHandler(value = BusinessException.class)
    public ResponseEntity parameterException(BusinessException exception) {
        log.error("BusinessException异常：{}", exception.getMessage());
        return ResponseEntity.status(exception.getHttpStatus()).body(R.failed(exception.getMessage()));
    }

    @ResponseBody
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity parameterException(MethodArgumentNotValidException exception) {
        log.error("MethodArgumentNotValidException异常：{}", exception.getBindingResult());
        List<ObjectError> allErrors = exception.getBindingResult().getAllErrors();
        return ResponseEntity.status(400).body(R.failed(allErrors.get(0).getDefaultMessage()));
    }

    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public ResponseEntity exception(Exception exception) {
        log.error("Exception异常：", exception);
        return ResponseEntity.status(500).body(R.failed(exception.getCause().getMessage()));
    }

}
