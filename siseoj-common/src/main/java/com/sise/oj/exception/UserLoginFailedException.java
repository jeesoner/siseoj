package com.sise.oj.exception;

/**
 * @author CiJee
 * @version 1.0
 */
public class UserLoginFailedException extends RuntimeException {

    public UserLoginFailedException(String message) {
        super(message);
    }
}
