package com.snowcattle.game.excutor.event;

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
        System.out.println(eventParams[0] + "float"+ eventParams[1]);
    }
}
