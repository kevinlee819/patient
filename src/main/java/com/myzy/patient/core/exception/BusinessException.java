package com.myzy.patient.core.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;


/**
 * 业务发生异常
 *
 * @author leekejin
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class BusinessException extends RuntimeException {

    private HttpStatus httpStatus;

    public BusinessException(String msg) {
        super(msg);
        this.httpStatus = HttpStatus.resolve(HttpStatusCodeEnum.INTERNAL_SERVER_ERROR.getCode());
    }

    public BusinessException(HttpStatusCodeEnum httpStatusCodeEnum) {
        super(httpStatusCodeEnum.getDefaultContent());
        this.httpStatus = HttpStatus.resolve(httpStatusCodeEnum.getCode());
    }

    public BusinessException(String msg, HttpStatusCodeEnum httpStatusCodeEnum) {
        super(msg == null ? httpStatusCodeEnum.getDefaultContent() : msg);
        this.httpStatus = HttpStatus.resolve(httpStatusCodeEnum.getCode());
    }

    public Integer getCode() {
        return this.httpStatus.value();
    }

}
