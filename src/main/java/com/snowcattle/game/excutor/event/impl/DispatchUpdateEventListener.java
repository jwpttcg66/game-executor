package com.snowcattle.game.excutor.event.impl;

import com.snowcattle.game.excutor.event.IEvent;
import com.snowcattle.game.excutor.thread.DispatchThread;

import java.util.concurrent.locks.LockSupport;

/**
 * Created by jiangwenping on 17/1/11.
 */
public class DispatchUpdateEventListener extends UpdateEventListener {
        private DispatchThread dispatchThread ;

    public DispatchUpdateEventListener(DispatchThread dispatchThread) {
        super();
        this.dispatchThread = dispatchThread;
    }

    public void fireEvent(IEvent event) {
        super.fireEvent(event);
//        UpdateEvent updateEvent = new UpdateEvent(Constants.EventTypeConstans.updateEventType, event.getParams());
//        this.dispatchThread.addUpdateEvent(updateEvent);

        //提交执行线程

        LockSupport.unpark(dispatchThread);
    }
}
