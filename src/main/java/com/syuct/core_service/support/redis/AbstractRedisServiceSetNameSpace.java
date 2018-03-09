package com.syuct.core_service.support.redis;


import com.syuct.core_service.config.RedisNameSpaceConfig;

/**
 * 自定义命名空间 相当于数据库自建表 在该命名空间内所有的key唯一
 * 当需要独立的命名空间时需要实现该抽象类并使用{@link RedisNameSpaceConfig}中的指定
 * @param <T>
 */
public abstract class AbstractRedisServiceSetNameSpace<T> extends AbstractRedisService<T> {
    @Override
    protected String getRedisKey() {
        return setNameSpace();
    }


    public abstract String setNameSpace();
}
