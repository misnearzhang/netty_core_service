package com.syuct.core_service.protoc;

import java.io.Serializable;

/**
 * 消息发起抽象 可用其他序列化工具代替
 *
 * @author Misnearzhang
 */
public class UserMessage implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 6808713478447609004L;
    private String from;//  friend group
    private String to;//	 friend
    private String type;//TEXT IMAGE
    private String content;//文本或者图片链接
    private String sign;//校验和


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

}
