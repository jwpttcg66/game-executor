package com.snowcattle.game.excutor.pool;

import com.snowcattle.game.excutor.thread.dispatch.DispatchThread;
import com.snowcattle.game.excutor.thread.update.LockSupportUpdateFuture;
import com.snowcattle.game.excutor.thread.update.LockSupportUpdateFutureThread;
import com.snowcattle.game.excutor.common.ThreadNameFactory;
import com.snowcattle.game.excutor.thread.listener.LockSupportUpdateFutureListener;
import com.snowcattle.game.excutor.entity.IUpdate;
import com.snowcattle.game.excutor.utils.Constants;
import com.snowcattle.game.excutor.utils.ExecutorUtil;
import com.snowcattle.game.thread.executor.NonOrderedQueuePoolExecutor;

import java.util.concurrent.*;

/**
 * Created by jiangwenping on 17/1/11.
 * 更新执行器
 */
public class UpdateExecutorService implements IUpdateExcutor{

    private NonOrderedQueuePoolExecutor nonOrderedQueuePoolExecutor;


    public UpdateExecutorService(int corePoolSize) {
        nonOrderedQueuePoolExecutor = new NonOrderedQueuePoolExecutor(corePoolSize);
    }

    @Override
    public void excutorUpdate(DispatchThread dispatchThread, IUpdate iUpdate, boolean firstFlag, int updateExcutorIndex) {
        LockSupportUpdateFuture lockSupportUpdateFuture = new LockSupportUpdateFuture(dispatchThread);
        lockSupportUpdateFuture.addListener(new LockSupportUpdateFutureListener());
        LockSupportUpdateFutureThread lockSupportUpdateFutureThread = new LockSupportUpdateFutureThread(dispatchThread, iUpdate, lockSupportUpdateFuture);
        nonOrderedQueuePoolExecutor.execute(lockSupportUpdateFutureThread);
    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {
        ExecutorUtil.shutdownAndAwaitTermination(nonOrderedQueuePoolExecutor, 60,
                TimeUnit.MILLISECONDS);
    }
}
