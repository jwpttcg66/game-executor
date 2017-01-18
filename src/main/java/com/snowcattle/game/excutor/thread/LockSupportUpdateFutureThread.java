package com.snowcattle.game.excutor.thread;

import com.snowcattle.game.excutor.event.EventBus;
import com.snowcattle.game.excutor.update.IUpdate;

/**
 * Created by jiangwenping on 17/1/18.
 */
public class LockSupportUpdateFutureThread implements Runnable {

    private DispatchThread dispatchThread;
    /**
     * 事件总线
     */
    private EventBus eventBus;
    private IUpdate iUpdate;
    private LockSupportUpdateFuture lockSupportUpdateFuture;

    public LockSupportUpdateFutureThread(DispatchThread dispatchThread, EventBus eventBus, IUpdate iUpdate
        , LockSupportUpdateFuture lockSupportUpdateFuture) {
        this.dispatchThread = dispatchThread;
        this.eventBus = eventBus;
        this.iUpdate = iUpdate;
        this.lockSupportUpdateFuture = lockSupportUpdateFuture;
    }

    public void run() {
        if (getiUpdate() != null) {
            IUpdate excutorUpdate = getiUpdate();
            excutorUpdate.update();
            setiUpdate(null);
            lockSupportUpdateFuture.setSuccess(true);
        }
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

    public IUpdate getiUpdate() {
        return iUpdate;
    }

    public void setiUpdate(IUpdate iUpdate) {
        this.iUpdate = iUpdate;
    }
}

