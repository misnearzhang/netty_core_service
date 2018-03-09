package com.syuct.core_service.support.pool;

import com.syuct.core_service.protoc.TransMessage;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

/**
 * Created by misnearzhang on 2017/5/9.
 */
public class PoolUtils {

    private static GenericObjectPool<TransMessage.Message.Builder> pool;
    private static GenericObjectPool<TransMessage.Head.Builder> head_pool;
    static {
        MessagePoolFactory messagePoolFactory = new MessagePoolFactory();
        HeaderPoolFactory headerPoolFactory = new HeaderPoolFactory();
        //资源池配置
        GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();
        poolConfig.setMinIdle(100);
        //创建资源池
        pool= new GenericObjectPool<TransMessage.Message.Builder>(messagePoolFactory,poolConfig);
        head_pool = new GenericObjectPool<TransMessage.Head.Builder>(headerPoolFactory,poolConfig);
    }
    public static TransMessage.Message.Builder getMessageInstance() {
        try {
            return pool.borrowObject();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static TransMessage.Head.Builder getHeaderInstance() {
        try {
            return head_pool.borrowObject();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public static void releaseMessage(TransMessage.Message.Builder message){
        pool.returnObject(message);
    }
    public static void releaseHeader(TransMessage.Head.Builder header){
        head_pool.returnObject(header);
    }

}
