package com.sise.oj.annotation;

import java.lang.annotation.*;

/**
 * 用于标记匿名访问方法
 *
 * @author Cijee
 * @version 1.0
 */
@Inherited
@Documented
@Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface AnonymousAccess {

}
