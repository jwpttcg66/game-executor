package com.snowcattle.game.excutor.event.syns;

import com.snowcattle.game.excutor.event.Event;
import com.snowcattle.game.excutor.event.EventParam;
import com.snowcattle.game.excutor.event.EventType;

/**
 * Created by jiangwenping on 17/1/11.
 */
public class CreateEvent extends Event {
    public CreateEvent(EventType eventType, EventParam... parms){
        setEventType(eventType);
        setParams(parms);
    }

    public void call() {
        EventParam[] eventParams = getParams();
        System.out.println(eventParams[0].getT() + "float"+ eventParams[1].getT());
    }
}
