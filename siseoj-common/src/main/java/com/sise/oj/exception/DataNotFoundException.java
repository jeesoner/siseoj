package com.sise.oj.exception;

/**
 * 数据不存在异常
 *
 * @author Cijee
 * @version 1.0
 */
public class DataNotFoundException extends RuntimeException {

    public DataNotFoundException(Class<?> clazz, String field, Object val) {
        super(generateMessage(clazz.getSimpleName(), field, val));
    }

    private static String generateMessage(String bean, String field, Object val) {
        return bean + " with " +  field + " " + val + " does not exist";
    }
}
