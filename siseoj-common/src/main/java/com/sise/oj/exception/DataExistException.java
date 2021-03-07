package com.sise.oj.exception;

/**
 * 数据已存在异常
 *
 * @author Cijee
 * @version 1.0
 */
public class DataExistException extends RuntimeException {

    public DataExistException(Class<?> clazz, String field, String val) {
        super(generateMessage(clazz.getSimpleName(), field, val));
    }

    public DataExistException(String message) {
        super(message);
    }

    private static String generateMessage(String bean, String field, String val) {
        return bean + " with " +  field + " " + val + " exist";
    }
}
