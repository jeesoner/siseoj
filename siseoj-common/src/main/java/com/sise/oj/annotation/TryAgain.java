package com.sise.oj.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 乐观锁注解
 *
 * @author Cijee
 * @version 1.0
 */
@Target(ElementType.METHOD) // 作用到方法上
@Retention(RetentionPolicy.RUNTIME)
public @interface TryAgain {

    /**
     * 重试次数
     *
     * @return Int
     */
    int tryTimes() default 5;
}
