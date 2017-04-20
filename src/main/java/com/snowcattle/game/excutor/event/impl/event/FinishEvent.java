package com.snowcattle.game.excutor.event.impl.event;

import com.snowcattle.game.excutor.event.AbstractEvent;
import com.snowcattle.game.excutor.event.EventParam;
import com.snowcattle.game.excutor.event.EventType;

/**
 * Created by jiangwenping on 17/1/11.
 * dispatch 使用
 */
public class FinishEvent extends AbstractEvent {
    public FinishEvent(EventType eventType, EventParam... parms){
        setEventType(eventType);
        setParams(parms);
    }

    public void call() {
//        EventParam[] eventParams = getParams();
//        System.out.println(eventParams[0].getT() + "float"+ eventParams[1].getT());
    }
}
