package com.snowcattle.game.excutor.pool;

import com.snowcattle.game.excutor.thread.ThreadNameFactory;
import com.snowcattle.game.excutor.utils.Constants;
import com.snowcattle.game.excutor.utils.ExecutorUtil;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by jiangwenping on 17/1/11.
 */
public class UpdateExecutorService {
    public UpdateExecutorService(ExecutorService executorService, int size) {
        this.executorService = executorService;
        this.threadSize = size;
    }

    private ExecutorService executorService;
    private int threadSize;

    public void startUp(){
        ThreadNameFactory threadNameFactory = new ThreadNameFactory(Constants.Thread.UPDATE);
        this.executorService = Executors.newCachedThreadPool(threadNameFactory);
        for (int i = 0; i < threadSize; i++) {
//            this.executorService.execute(new UpdateThread());
        }
    }

    public void shutDown(){
        if (this.executorService != null) {
            ExecutorUtil.shutdownAndAwaitTermination(this.executorService, 60,
                    TimeUnit.MILLISECONDS);
            this.executorService = null;
        }

    }

}
