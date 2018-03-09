package com.syuct.core_service.core.container;

import com.syuct.core_service.core.exception.NotOnlineException;
import com.syuct.core_service.protoc.TransMessage;
import io.netty.channel.Channel;
import io.netty.channel.ChannelId;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.ChannelMatchers;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.DefaultEventExecutor;
import io.netty.util.concurrent.EventExecutor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.ConcurrentHashMap;

/**
 * 用户容器 存储所有在线用户  可以抽取出来用redis等nosql实现
 *
 * @author Misnearzhang
 */
public class Container {
    private static final Logger logger = LogManager.getLogger(Container.class);

    //userAccount : ChannelId   //  用户账户  channelId
    private static ConcurrentHashMap<String, UserAccount> accountConcurrentHashMap = new ConcurrentHashMap<String, UserAccount>(100000);
    private static ConcurrentHashMap<ChannelId, UserAccount> channelIdUserAccountConcurrentHashMap = new ConcurrentHashMap<ChannelId, UserAccount>(100000);

    private static EventExecutor executors = new DefaultEventExecutor();//channel使用的线程池
    private static ChannelGroup group = new DefaultChannelGroup(executors);//维护 所有在线的channel

    /**
     * 根据userAccount 获取channelId
     *
     * @param userAccount
     * @return
     */
    public static ChannelId getChannelId(String userAccount) {
        logger.info(accountConcurrentHashMap.containsKey(userAccount));
        if (accountConcurrentHashMap.containsKey(userAccount)) {
            logger.info(userAccount);
            return accountConcurrentHashMap.get(userAccount).getChannelId();
        } else {
            return null;
        }
    }

    public static Channel getChannel(ChannelId channelId) {
        return group.find(channelId);
    }

    /**
     * 保存用户相关的 一些数据
     *
     * @param userName
     * @param channelId
     * @return
     */
    public static boolean addOrReplace(String userName, ChannelId channelId) {
        UserAccount userAccount = new UserAccount(userName, channelId);
        if (accountConcurrentHashMap.containsKey(userName)) {
            accountConcurrentHashMap.replace(userName, userAccount);
            channelIdUserAccountConcurrentHashMap.replace(channelId, userAccount);
        } else {
            accountConcurrentHashMap.put(userName, userAccount);
            channelIdUserAccountConcurrentHashMap.put(channelId, userAccount);
        }
        return true;
    }

    public static boolean isLogin(String userName) {
        return accountConcurrentHashMap.containsKey(userName);
    }

    public static boolean isLogin(ChannelId channelId) {
        boolean containsKey = channelIdUserAccountConcurrentHashMap.containsKey(channelId);
        if (containsKey) {
            UserAccount account = channelIdUserAccountConcurrentHashMap.get(channelId);
            if (accountConcurrentHashMap.containsKey(account.getAccount())) {
                return true;
            } else {
                channelIdUserAccountConcurrentHashMap.remove(channelId);
                return false;
            }
        } else {
            return false;
        }
    }

    /**
     * 登出 清理 accountConcurrentHashMap 和 channelIdUserAccountConcurrentHashMap中数据
     *
     * @param channelId
     * @return
     */
    public static boolean logOut(ChannelId channelId) {
        if (channelIdUserAccountConcurrentHashMap.containsKey(channelId)) {
            if (accountConcurrentHashMap.containsKey(channelIdUserAccountConcurrentHashMap.get(channelId).getAccount())) {
                accountConcurrentHashMap.remove(channelIdUserAccountConcurrentHashMap.get(channelId).getAccount());
            }
            channelIdUserAccountConcurrentHashMap.remove(channelId);
        }
        group.remove(channelId);
        return true;
    }

    //将用户心跳标识加1
    public static void pingPongCountAdd(ChannelId channelId) {
        channelIdUserAccountConcurrentHashMap.get(channelId).addCount();
    }

    public static void pingPongRest(ChannelId channelId) {
        if (channelIdUserAccountConcurrentHashMap.containsKey(channelId)) {
            channelIdUserAccountConcurrentHashMap.get(channelId).CountRest();
        }
    }

    //获得心跳数
    public static int getPingPongCount(ChannelId channelId) {
        return channelIdUserAccountConcurrentHashMap.get(channelId).getHeartBeatCount();
    }

    //用户在线数量
    public static int getCount() {
        return group.size();
    }


    /**
     * 发送信息
     *
     * @param obj
     * @param channelId
     */
    public static void send(final TransMessage.Message obj, ChannelId channelId) throws NotOnlineException {
        if (isLogin(channelId)) {
            Channel channel = group.find(channelId);
            logger.info(channel);
            group.writeAndFlush(obj, ChannelMatchers.is(channel));
        } else {
            throw new NotOnlineException();
        }

    }

    public static void sendHeartBeat(final TransMessage.Message obj, ChannelId channelId) {
        Channel channel = group.find(channelId);
        group.writeAndFlush(obj, ChannelMatchers.is(channel));
    }

    public static void addChannel(Channel channel) {
        group.add(channel);
    }

    public static void removeChannel(Channel channel) {
        if(group.contains(channel)){
            group.remove(channel);
        }
    }

}
