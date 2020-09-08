package com.myzy.patient.core.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author leekejin
 * @date 2020-02-14 17:17
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result<T> {

    @ApiModelProperty(value = "业务错误码", required = true)
    private long code;

    @ApiModelProperty(value = "结果集")
    private T data;

    @ApiModelProperty(value = "消息描述")
    private String msg;

    public static Result ok() {
        return new Result<>(0, null, null);
    }

    public static <T> Result<T> ok(T data) {
        return new Result<>(0, data, null);
    }

    public static Result<String> failed(String message) {
        return new Result<>(-1, null, message);
    }

}
