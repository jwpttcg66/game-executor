package com.snowcattle.game.excutor.event.impl;

import com.snowcattle.game.excutor.event.EventBus;
import com.snowcattle.game.excutor.event.EventParam;
import com.snowcattle.game.excutor.event.IEvent;
import com.snowcattle.game.excutor.pool.UpdateExecutorService;
import com.snowcattle.game.excutor.thread.LockSupportDisptachThread;
import com.snowcattle.game.excutor.thread.LockSupportUpdateFuture;
import com.snowcattle.game.excutor.thread.LockSupportUpdateFutureThread;
import com.snowcattle.game.excutor.thread.listener.LockSupportUpdateFutureListener;
import com.snowcattle.game.excutor.update.IUpdate;
import com.snowcattle.game.excutor.utils.Constants;

/**
 * Created by jiangwenping on 17/1/11.
 */
public class DispatchUpdateEventListener extends UpdateEventListener {
    private LockSupportDisptachThread dispatchThread;
    private EventBus updateServiceEventBus;

    public DispatchUpdateEventListener(LockSupportDisptachThread dispatchThread, EventBus updateServiceEventBus) {
        this.dispatchThread = dispatchThread;
        this.updateServiceEventBus = updateServiceEventBus;
    }

    public void fireEvent(IEvent event) {
        super.fireEvent(event);

        //提交执行线程
        EventParam[] eventParams = event.getParams();
        IUpdate iUpdate = (IUpdate) eventParams[0].getT();
        if(iUpdate.isActive()) {
            LockSupportUpdateFuture lockSupportUpdateFuture = new LockSupportUpdateFuture(dispatchThread);
            lockSupportUpdateFuture.addListener(new LockSupportUpdateFutureListener());
            UpdateExecutorService updateExecutorService = dispatchThread.getUpdateExecutorService();
            LockSupportUpdateFutureThread lockSupportUpdateFutureThread = new LockSupportUpdateFutureThread(dispatchThread, iUpdate, lockSupportUpdateFuture);
            updateExecutorService.submit(lockSupportUpdateFutureThread);
        }else{
            ReadFinishEvent finishEvent = new ReadFinishEvent(Constants.EventTypeConstans.readyFinishEventType, iUpdate.getId(), event.getParams());
            updateServiceEventBus.addEvent(finishEvent);
        }
    }
}
