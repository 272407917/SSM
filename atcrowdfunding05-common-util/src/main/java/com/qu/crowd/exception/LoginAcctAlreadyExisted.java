package com.qu.crowd.exception;

/**
 * @author 瞿琮
 * @create 2020-07-02 18:03
 */
public class LoginAcctAlreadyExisted extends RuntimeException {
    public LoginAcctAlreadyExisted() {
    }

    public LoginAcctAlreadyExisted(String message) {
        super(message);
    }

    public LoginAcctAlreadyExisted(String message, Throwable cause) {
        super(message, cause);
    }

    public LoginAcctAlreadyExisted(Throwable cause) {
        super(cause);
    }

    public LoginAcctAlreadyExisted(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
