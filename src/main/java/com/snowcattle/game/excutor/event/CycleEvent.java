package com.snowcattle.game.excutor.event;

import java.io.Serializable;

/**
 * Created by jiangwenping on 17/1/16.
 */
public class CycleEvent<ID extends Serializable>  extends AbstractEvent{

    private ID id;

    public CycleEvent(){

    }

    public CycleEvent(EventType eventType, ID eventId, EventParam... parms){
        setEventType(eventType);
        setParams(parms);
        this.id = eventId;
    }

    @Override
    public void call() {

    }

    public ID getId() {
        return id;
    }

    public void setId(ID id) {
        this.id = id;
    }

}
