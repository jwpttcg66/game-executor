package com.snowcattle.game.excutor.thread;

import com.snowcattle.game.excutor.event.EventBus;
import com.snowcattle.game.excutor.update.IUpdate;

/**
 * Created by jiangwenping on 17/1/9.
 * 事件执行器
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
        iUpdate = null;
    }

    public IUpdate getiUpdate() {
        return iUpdate;
    }

    public void setiUpdate(IUpdate iUpdate) {
        this.iUpdate = iUpdate;
    }

    public DispatchThread getDispatchThread() {
        return dispatchThread;
    }

    public void setDispatchThread(DispatchThread dispatchThread) {
        this.dispatchThread = dispatchThread;
    }

    public EventBus getEventBus() {
        return eventBus;
    }

    public void setEventBus(EventBus eventBus) {
        this.eventBus = eventBus;
    }
}
