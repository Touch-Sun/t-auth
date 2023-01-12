package com.touchsun.common.exception;

/**
 * oauth-template
 *
 * @author lee
 * @since 2023/1/4 22:44
 */
public class BaseException extends RuntimeException {

    public BaseException(String message) {
        super(message);
    }

    public BaseException(String message, Throwable cause) {
        super(message, cause);
    }
}
