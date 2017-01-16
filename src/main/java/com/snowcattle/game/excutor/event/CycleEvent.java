package com.snowcattle.game.excutor.event;

/**
 * Created by jiangwenping on 17/1/16.
 */
public class CycleEvent extends Event {

    private long id;

    public CycleEvent(EventType eventType, long eventId, EventParam... parms){
        setEventType(eventType);
        setParams(parms);
        this.id = eventId;
    }

    @Override
    public void call() {

    }

    public long getId() {
        return id;
    }
}
