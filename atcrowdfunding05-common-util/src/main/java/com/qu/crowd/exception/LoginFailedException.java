package com.qu.crowd.exception;

/**
 * @author 瞿琮
 * @create 2020-06-30 16:31
 */
public class LoginFailedException extends RuntimeException {

    //异常类序列化号码，唯一的
    static final long serialVersionUID = -70348971907341415L;

    public LoginFailedException() {
    }

    public LoginFailedException(String message) {
        super(message);
    }

    public LoginFailedException(String message, Throwable cause) {
        super(message, cause);
    }

    public LoginFailedException(Throwable cause) {
        super(cause);
    }

    public LoginFailedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
