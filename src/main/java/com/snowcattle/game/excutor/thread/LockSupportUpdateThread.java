package com.snowcattle.game.excutor.thread;

import com.snowcattle.game.excutor.event.EventBus;

import java.util.concurrent.locks.LockSupport;

/**
 * Created by jiangwenping on 17/1/9.
 * 带预置锁的执行器
 */
public class LockSupportUpdateThread extends UpdateThread{

    public LockSupportUpdateThread(DispatchThread dispatchThread, EventBus eventBus) {
        super(dispatchThread, eventBus);
    }

    public void run() {
        getiUpdate().update();
        LockSupport.unpark(getDispatchThread());
    }

}
