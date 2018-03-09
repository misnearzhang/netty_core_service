package com.syuct.core_service.core.exception;

/**
 * Created by zhanglong on 17-4-9.
 */
public class NotOnlineException extends Exception {

    @Override
    public String getMessage() {
        //return super.getMessage();
        return "err: this user is not online ";
    }
}
