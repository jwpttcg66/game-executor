package com.snowcattle.game.excutor.thread;

import com.snowcattle.future.AbstractFuture;
import com.snowcattle.future.ITaskFuture;
import com.snowcattle.game.excutor.event.EventBus;
import com.snowcattle.game.excutor.update.IUpdate;

/**
 * Created by jiangwenping on 17/1/18.
 */
public class LockSupportUpdateFuture extends AbstractFuture<IUpdate> {

    private DispatchThread dispatchThread;
    private EventBus updateServiceeventBus;

    public LockSupportUpdateFuture(DispatchThread dispatchThread, EventBus updateServiceeventBus) {
        this.dispatchThread = dispatchThread;
        this.updateServiceeventBus = updateServiceeventBus;
    }

    @Override
    public ITaskFuture<IUpdate> setSuccess(Object result) {
        return super.setSuccess(result);
    }

    @Override
    public ITaskFuture<IUpdate> setFailure(Throwable cause) {
        return super.setFailure(cause);
    }

    public DispatchThread getDispatchThread() {
        return dispatchThread;
    }

    public void setDispatchThread(DispatchThread dispatchThread) {
        this.dispatchThread = dispatchThread;
    }

    public EventBus getUpdateServiceeventBus() {
        return updateServiceeventBus;
    }

    public void setUpdateServiceeventBus(EventBus updateServiceeventBus) {
        this.updateServiceeventBus = updateServiceeventBus;
    }
}