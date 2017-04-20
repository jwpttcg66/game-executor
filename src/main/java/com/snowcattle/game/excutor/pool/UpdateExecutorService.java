package com.snowcattle.game.excutor.pool;

import com.snowcattle.game.excutor.thread.dispatch.DispatchThread;
import com.snowcattle.game.excutor.thread.update.LockSupportUpdateFuture;
import com.snowcattle.game.excutor.thread.update.LockSupportUpdateFutureThread;
import com.snowcattle.game.excutor.common.ThreadNameFactory;
import com.snowcattle.game.excutor.thread.listener.LockSupportUpdateFutureListener;
import com.snowcattle.game.excutor.entity.IUpdate;
import com.snowcattle.game.excutor.utils.Constants;
import com.snowcattle.game.excutor.utils.ExecutorUtil;

import java.util.concurrent.*;

/**
 * Created by jiangwenping on 17/1/11.
 * 更新执行器
 */
public class UpdateExecutorService extends ThreadPoolExecutor implements IUpdateExcutor{


    public UpdateExecutorService(int corePoolSize, long keepAliveTime, TimeUnit unit) {
        super(corePoolSize, Integer.MAX_VALUE, keepAliveTime, unit, new SynchronousQueue<Runnable>(),
                new ThreadNameFactory(Constants.Thread.UPDATE), new AbortPolicy());
    }

    public UpdateExecutorService(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit,
                                 ThreadFactory threadFactory, RejectedExecutionHandler rejectedExecutionHandler) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, new SynchronousQueue<Runnable>(),
                threadFactory, new AbortPolicy());
    }

    @Override
    public void excutorUpdate(DispatchThread dispatchThread, IUpdate iUpdate, boolean initFlag, int updateExcutorIndex) {
        LockSupportUpdateFuture lockSupportUpdateFuture = new LockSupportUpdateFuture(dispatchThread);
        lockSupportUpdateFuture.addListener(new LockSupportUpdateFutureListener());
        LockSupportUpdateFutureThread lockSupportUpdateFutureThread = new LockSupportUpdateFutureThread(dispatchThread, iUpdate, lockSupportUpdateFuture);
        submit(lockSupportUpdateFutureThread);
    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {
        ExecutorUtil.shutdownAndAwaitTermination(this, 60,
                TimeUnit.MILLISECONDS);
    }
}
