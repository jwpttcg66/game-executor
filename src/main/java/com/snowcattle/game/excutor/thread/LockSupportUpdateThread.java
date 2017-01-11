package com.snowcattle.game.excutor.thread;

import com.snowcattle.game.excutor.event.EventBus;

import java.util.concurrent.locks.LockSupport;

/**
 * Created by jiangwenping on 17/1/9.
 * 带预置锁的执行器
 */
public class LockSupportUpdateThread extends UpdateThread{

    private DispatchThread dispatchThread;

    public LockSupportUpdateThread(DispatchThread dispatchThread, EventBus eventBus) {
        super(eventBus);
        this.dispatchThread = dispatchThread;
    }

    public void run() {
        if(getiUpdate() != null) {
            getiUpdate().update();
            setiUpdate(null);
            LockSupport.unpark(getDispatchThread());
        }
    }


    public DispatchThread getDispatchThread() {
        return dispatchThread;
    }
}
