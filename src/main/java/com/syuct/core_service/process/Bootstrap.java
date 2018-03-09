package com.syuct.core_service.process;

import com.syuct.core_service.config.SystemConfig;
import com.syuct.core_service.core.server.Server;
import com.syuct.core_service.core.server.ThreadPool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

/**
 * Created by misnearzhang on 5/16/17.
 */

@Component
public class Bootstrap implements CommandLineRunner{

    @Autowired
    SystemConfig systemConfig;

    @Override
    public void run(String... args) throws Exception {
        try {
            Server server=new Server();
            ThreadPool threadPool = new ThreadPool(systemConfig.getThreadCorePoolSize(),systemConfig.getThreadMaximumPoolSize(),systemConfig.getThreadKeepAliveTime(),10000);
            threadPool.reflectParse(HandMessage.class);
            threadPool.init();
            server.setThreadPool(threadPool);
            server.bind(systemConfig.getTcpPort(),systemConfig.getIdleReadTime(),systemConfig.getIdleWriteTime());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
