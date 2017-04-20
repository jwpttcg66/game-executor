package com.snowcattle.game.excutor.event.impl.event;

import com.snowcattle.game.excutor.event.CycleEvent;
import com.snowcattle.game.excutor.event.EventParam;
import com.snowcattle.game.excutor.event.EventType;

/**
 * Created by jiangwenping on 17/1/16.
 * updateService使用
 */
public class ReadyCreateEvent extends CycleEvent {

    public ReadyCreateEvent(EventType eventType, long eventId, EventParam... parms){
        super(eventType, eventId, parms);
    }

    public void call() {

    }
}
