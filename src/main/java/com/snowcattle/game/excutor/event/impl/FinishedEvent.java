package com.snowcattle.game.excutor.event.impl;

import com.snowcattle.game.excutor.event.CycleEvent;
import com.snowcattle.game.excutor.event.EventParam;
import com.snowcattle.game.excutor.event.EventType;

import java.io.Serializable;

/**
 * Created by jiangwenping on 17/2/21.
 * updateService使用
 */
public class FinishedEvent <ID extends Serializable> extends CycleEvent{

    public FinishedEvent(EventType eventType,ID eventId, EventParam... parms){
        super(eventType, eventId, parms);
    }

    public void call() {
//        EventParam[] eventParams = getParams();
//        System.out.println(eventParams[0].getT() + "float"+ eventParams[1].getT());
    }
}
