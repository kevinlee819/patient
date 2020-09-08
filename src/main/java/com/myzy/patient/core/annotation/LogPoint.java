package com.myzy.patient.core.annotation;

import java.lang.annotation.*;

/**
 * 日志记录埋点
 *
 * @author leekejin
 * @date 2020-07-16 17:26
 */
@Inherited
@Documented
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface LogPoint {

    /**
     * 日志的消息内容。支持SpEL表达式，参数可以使用#号加参数名称，也可以使用#p加参数下标
     */
    String message();

    /**
     * 日志分类
     */
    String type() default "operation";

}
