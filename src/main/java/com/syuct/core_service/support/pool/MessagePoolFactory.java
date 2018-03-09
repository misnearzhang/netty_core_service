package com.syuct.core_service.support.pool;

import com.syuct.core_service.protoc.TransMessage;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.PooledObjectFactory;
import org.apache.commons.pool2.impl.DefaultPooledObject;

/**
 * Created by misnearzhang on 2017/5/9.
 */
public class MessagePoolFactory implements PooledObjectFactory {
    @Override
    public PooledObject makeObject() throws Exception {
        TransMessage.Message.Builder message = TransMessage.Message.newBuilder();
        return new DefaultPooledObject<TransMessage.Message.Builder>(message);
    }

    @Override
    public void destroyObject(PooledObject pooledObject) throws Exception {

    }

    @Override
    public boolean validateObject(PooledObject pooledObject) {
        return false;
    }

    @Override
    public void activateObject(PooledObject pooledObject) throws Exception {

    }

    @Override
    public void passivateObject(PooledObject pooledObject) throws Exception {

    }
}
