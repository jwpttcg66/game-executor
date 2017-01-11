package com.snowcattle.game.excutor.pool;

import com.snowcattle.game.excutor.thread.ThreadNameFactory;
import com.snowcattle.game.excutor.utils.Constants;
import com.snowcattle.game.excutor.utils.ExecutorUtil;

import java.util.concurrent.*;

/**
 * Created by jiangwenping on 17/1/11.
 * 更新执行器
 */
public class UpdateExecutorService extends ThreadPoolExecutor {


    public UpdateExecutorService(int corePoolSize, long keepAliveTime, TimeUnit unit) {
        super(corePoolSize, Integer.MAX_VALUE, keepAliveTime, unit, new SynchronousQueue<Runnable>(),
                new ThreadNameFactory(Constants.Thread.UPDATE), new AbortPolicy());
    }

    public UpdateExecutorService(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit,
                                 ThreadFactory threadFactory, RejectedExecutionHandler rejectedExecutionHandler) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, new SynchronousQueue<Runnable>(),
                threadFactory, new AbortPolicy());
    }

    public void shutDown() {
        ExecutorUtil.shutdownAndAwaitTermination(this, 60,
                TimeUnit.MILLISECONDS);

    }

}
