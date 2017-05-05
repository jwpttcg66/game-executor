package com.snowcattle.game.executor.update.pool;

import com.snowcattle.game.executor.update.pool.excutor.BindThreadEventExecutorService;
import com.snowcattle.game.executor.update.thread.dispatch.DispatchThread;
import com.snowcattle.game.executor.update.entity.IUpdate;
import com.snowcattle.game.executor.common.utils.ExecutorUtil;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by jwp on 2017/2/23.
 */
public class UpdateBindExecutorService implements IUpdateExecutor {

    private int excutorSize;

    private BindThreadEventExecutorService[] bindThreadEventExecutorServices;

    private final AtomicInteger idx = new AtomicInteger();

    private DispatchThread dispatchThread;

    public UpdateBindExecutorService(int excutorSize) {
        this.excutorSize = excutorSize;
    }

    public void startup() {
        bindThreadEventExecutorServices = new BindThreadEventExecutorService[excutorSize];
        for (int i = 0; i < excutorSize; i++) {
            bindThreadEventExecutorServices[i] = new BindThreadEventExecutorService(i, dispatchThread);
        }
    }

    @Override
    public void shutdown() {
        for (int i = 0; i < excutorSize; i++) {
            ExecutorUtil.shutdownAndAwaitTermination(bindThreadEventExecutorServices[i], 60,
                    TimeUnit.MILLISECONDS);
        }
    }

    public BindThreadEventExecutorService getNext() {
        return bindThreadEventExecutorServices[idx.getAndIncrement() % excutorSize];
    }

    @Override
    public void executorUpdate(DispatchThread dispatchThread, IUpdate iUpdate, boolean firstFlag, int updateExcutorIndex) {
        if(firstFlag) {
            BindThreadEventExecutorService bindThreadEventExecutorService = getNext();
            bindThreadEventExecutorService.excuteUpdate(iUpdate, firstFlag);
        }else{

        //查找老的更新器
//            BindThreadEventExecutorService bindThreadEventExecutorService = bindThreadEventExecutorServices[updateExcutorIndex];
            //完全随机，取消查找老的模块，使cpu更加平均。
            BindThreadEventExecutorService bindThreadEventExecutorService = getNext();
            bindThreadEventExecutorService.excuteUpdate(iUpdate, false);
        }
    }

    public DispatchThread getDispatchThread() {
        return dispatchThread;
    }

    public void setDispatchThread(DispatchThread dispatchThread) {
        this.dispatchThread = dispatchThread;
    }
}
