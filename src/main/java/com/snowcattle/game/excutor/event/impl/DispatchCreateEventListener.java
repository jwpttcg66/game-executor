package com.snowcattle.game.excutor.event.impl;

import com.snowcattle.game.excutor.event.EventBus;
import com.snowcattle.game.excutor.event.EventParam;
import com.snowcattle.game.excutor.event.IEvent;
import com.snowcattle.game.excutor.thread.DispatchThread;
import com.snowcattle.game.excutor.update.IUpdate;
import com.snowcattle.game.excutor.utils.Constants;

/**
 * Created by jiangwenping on 17/1/11.
 */
public class DispatchCreateEventListener extends CreateEventListener {

    private DispatchThread dispatchThread;
    private EventBus updateServiceEventBus;

    public DispatchCreateEventListener(DispatchThread dispatchThread, EventBus updateServiceEventBus) {
        super();
        this.dispatchThread = dispatchThread;
        this.updateServiceEventBus = updateServiceEventBus;
    }

    public void fireEvent(IEvent event) {
        super.fireEvent(event);
        EventParam[] eventParams = event.getParams();
        IUpdate iUpdate = (IUpdate) eventParams[0].getT();
        if(iUpdate.isActive()) {
            UpdateEvent updateEvent = new UpdateEvent(Constants.EventTypeConstans.updateEventType, event.getParams());
            this.dispatchThread.addUpdateEvent(updateEvent);
        }else{
            ReadFinishEvent finishEvent = new ReadFinishEvent(Constants.EventTypeConstans.readyFinishEventType, iUpdate.getId(), event.getParams());
            this.dispatchThread.addUpdateEvent(finishEvent);
        }
    }
}
