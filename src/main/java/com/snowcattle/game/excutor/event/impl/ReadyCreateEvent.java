package com.snowcattle.game.excutor.event.impl;

import com.snowcattle.game.excutor.event.Event;
import com.snowcattle.game.excutor.event.EventParam;
import com.snowcattle.game.excutor.event.EventType;

/**
 * Created by jiangwenping on 17/1/16.
 */
public class ReadyCreateEvent extends Event {

    public ReadyCreateEvent(EventType eventType, EventParam... parms){
        setEventType(eventType);
        setParams(parms);
    }

    public void call() {

    }
}
