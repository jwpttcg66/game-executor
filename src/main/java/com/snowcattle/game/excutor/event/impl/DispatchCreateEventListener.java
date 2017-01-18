package com.snowcattle.game.excutor.event.impl;

import com.snowcattle.game.excutor.event.IEvent;
import com.snowcattle.game.excutor.thread.DispatchThread;
import com.snowcattle.game.excutor.utils.Constants;

/**
 * Created by jiangwenping on 17/1/11.
 */
public class DispatchCreateEventListener extends CreateEventListener {

    private DispatchThread dispatchThread;

    public DispatchCreateEventListener(DispatchThread dispatchThread) {
        super();
        this.dispatchThread = dispatchThread;
    }

    public void fireEvent(IEvent event) {
        super.fireEvent(event);
        UpdateEvent updateEvent = new UpdateEvent(Constants.EventTypeConstans.updateEventType, event.getParams());
        this.dispatchThread.addUpdateEvent(updateEvent);
    }
}
