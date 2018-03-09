package com.syuct.core_service.protoc;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by Misnearzhang on 2017/4/24.
 */
public class SystemMessage implements Serializable{
    private String type;//系统消息类型
    private String to;//ALL : LLA
    private String desc;//描述
    private Map ext;//扩展字段

    public String getType() {
        return type;
    }

    public SystemMessage setType(String type) {
        this.type = type;
        return this;
    }

    public String getTo() {
        return to;
    }

    public SystemMessage setTo(String to) {
        this.to = to;
        return this;
    }

    public String getDesc() {
        return desc;
    }

    public SystemMessage setDesc(String desc) {
        this.desc = desc;
        return this;
    }

    public Map getExt() {
        return ext;
    }

    public SystemMessage setExt(Map ext) {
        this.ext = ext;
        return this;
    }
}
