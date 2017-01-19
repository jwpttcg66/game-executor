package com.snowcattle.game.excutor.event.impl;

import com.snowcattle.game.excutor.event.EventBus;
import com.snowcattle.game.excutor.event.EventParam;
import com.snowcattle.game.excutor.event.IEvent;
import com.snowcattle.game.excutor.service.UpdateService;
import com.snowcattle.game.excutor.thread.DispatchThread;
import com.snowcattle.game.excutor.update.IUpdate;
import com.snowcattle.game.excutor.utils.Constants;

/**
 * Created by jiangwenping on 17/1/11.
 */
public class DispatchCreateEventListener extends CreateEventListener {

    private DispatchThread dispatchThread;
//    private EventBus updateServiceEventBus;

    private UpdateService updateService;
    public DispatchCreateEventListener(DispatchThread dispatchThread, UpdateService updateService) {
        super();
        this.dispatchThread = dispatchThread;
//        this.updateServiceEventBus = updateServiceEventBus;
        this.updateService = updateService;
    }

    public void fireEvent(IEvent event) {
        super.fireEvent(event);
        EventParam[] eventParams = event.getParams();
        IUpdate iUpdate = (IUpdate) eventParams[0].getT();
        if(iUpdate.isActive()) {
            UpdateEvent updateEvent = new UpdateEvent(Constants.EventTypeConstans.updateEventType, event.getParams());
            this.dispatchThread.addUpdateEvent(updateEvent);
        }else{
            FinishEvent finishEvent = new FinishEvent(Constants.EventTypeConstans.finishEventType, eventParams);
            dispatchThread.addFinishEvent(finishEvent);
        }
    }
}
