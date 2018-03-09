package com.syuct.core_service.core.container;

import io.netty.channel.ChannelId;

import java.io.Serializable;

/**
 * Created by Misnearzhang on 2017/3/6.
 */
public class UserAccount implements Serializable {
    private String account;
    private ChannelId channelId;
    private int heartBeatCount;

    public UserAccount(String account, ChannelId channelId) {
        this.account = account;
        this.channelId = channelId;
        this.heartBeatCount = 0;
    }

    public String getAccount() {
        return account;
    }

    public UserAccount setAccount(String account) {
        this.account = account;
        return this;
    }

    public ChannelId getChannelId() {
        return channelId;
    }

    public UserAccount setChannelId(ChannelId channelId) {
        this.channelId = channelId;
        return this;
    }

    public int getHeartBeatCount() {
        return heartBeatCount;
    }

    public UserAccount setHeartBeatCount(int heartBeatCount) {
        this.heartBeatCount = heartBeatCount;
        return this;
    }

    public void addCount() {
        this.heartBeatCount++;
    }

    public void CountRest() {
        this.heartBeatCount = 0;
    }
}
