package com.snowcattle.game.excutor.event.impl.listener;

import com.snowcattle.game.excutor.event.EventParam;
import com.snowcattle.game.excutor.event.common.IEvent;
import com.snowcattle.game.excutor.event.impl.event.FinishEvent;
import com.snowcattle.game.excutor.event.impl.event.UpdateEvent;
import com.snowcattle.game.excutor.service.UpdateService;
import com.snowcattle.game.excutor.thread.dispatch.DispatchThread;
import com.snowcattle.game.excutor.entity.IUpdate;
import com.snowcattle.game.excutor.utils.Constants;

/**
 * Created by jiangwenping on 17/1/11.
 */
public class DispatchCreateEventListener extends CreateEventListener {

    private DispatchThread dispatchThread;

    private UpdateService updateService;
    public DispatchCreateEventListener(DispatchThread dispatchThread, UpdateService updateService) {
        super();
        this.dispatchThread = dispatchThread;
        this.updateService = updateService;
    }

    public void fireEvent(IEvent event) {
        super.fireEvent(event);
        EventParam[] eventParams = event.getParams();
        IUpdate iUpdate = (IUpdate) eventParams[0].getT();
        if(iUpdate.isActive()) {
            UpdateEvent updateEvent = new UpdateEvent(Constants.EventTypeConstans.updateEventType, iUpdate.getId(), event.getParams());
            updateEvent.setInitFlag(true);
            this.dispatchThread.addUpdateEvent(updateEvent);
        }else{
            FinishEvent finishEvent = new FinishEvent(Constants.EventTypeConstans.finishEventType, iUpdate.getId(), eventParams);
            dispatchThread.addFinishEvent(finishEvent);
        }
    }
}
