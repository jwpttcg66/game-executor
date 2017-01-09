package com.snowcattle.game.excutor.thread;

import com.snowcattle.game.excutor.event.EventBus;
import com.snowcattle.game.excutor.update.IUpdate;

import java.util.concurrent.locks.LockSupport;

/**
 * Created by jiangwenping on 17/1/9.
 */
public class UpdateThread implements Runnable{
    /**
     * 调度线程
     */
    private DispatchThread dispatchThread;
    /**
     * 事件总线
     */
    private EventBus eventBus;


    private IUpdate iUpdate;

    public UpdateThread(DispatchThread dispatchThread, EventBus eventBus) {
        this.dispatchThread = dispatchThread;
        this.eventBus = eventBus;
    }

    public void run() {
        iUpdate.update();
        LockSupport.unpark(dispatchThread);
        iUpdate = null;
    }

    public IUpdate getiUpdate() {
        return iUpdate;
    }

    public void setiUpdate(IUpdate iUpdate) {
        this.iUpdate = iUpdate;
    }
}
