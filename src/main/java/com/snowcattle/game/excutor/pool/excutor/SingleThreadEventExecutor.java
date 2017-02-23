package com.snowcattle.game.excutor.pool.excutor;

import java.util.concurrent.*;

/**
 * Created by jwp on 2017/2/23.
 * 单线程
 *
 * 当线程执行完所有update的时候，退出eventloop
 */
public class SingleThreadEventExecutor extends  FinalizableDelegatedExecutorService implements OrderedEventExecutor{

    //是否处于事件循环中
    private boolean eventLoopFlag;

    public SingleThreadEventExecutor() {
        super(new ThreadPoolExecutor(1, 1,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>()));
    }

    @Override
    public boolean inEventLoop() {
        return eventLoopFlag;
    }

    public void setEventLoopFlag(boolean eventLoopFlag) {
        this.eventLoopFlag = eventLoopFlag;
    }
}
