package com.snowcattle.game.excutor.event.impl.event;

import com.snowcattle.game.excutor.event.AbstractEvent;
import com.snowcattle.game.excutor.event.EventParam;
import com.snowcattle.game.excutor.event.EventType;
import com.snowcattle.game.excutor.utils.Loggers;

/**
 * Created by jiangwenping on 17/1/11.
 *  dispatch thread使用
 */
public class CreateEvent extends AbstractEvent {
    public CreateEvent(EventType eventType, EventParam... parms){
        setEventType(eventType);
        setParams(parms);
    }

    public void call() {
        if(Loggers.utilLogger.isDebugEnabled()){
            EventParam[] eventParams = getParams();
            Loggers.utilLogger.debug("create event " + eventParams[0].getT());
        }

    }
}
