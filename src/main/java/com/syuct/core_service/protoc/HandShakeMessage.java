package com.syuct.core_service.protoc;

import java.io.Serializable;

/**
 * Created by Misnearzhang on 2017/3/28.
 */
public class HandShakeMessage implements Serializable{

    private String account;
    private String password;

    public String getAccount() {
        return account;
    }

    public HandShakeMessage setAccount(String account) {
        this.account = account;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public HandShakeMessage setPassword(String password) {
        this.password = password;
        return this;
    }
}
