package com.snowcattle.game.excutor.event;

import com.snowcattle.game.excutor.event.common.IEvent;

/**
 * Created by jiangwenping on 17/1/9.
 */
public abstract  class Event implements IEvent {

    private EventType eventType;
    private EventParam[] eventParamps;

    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }

    public EventType getEventType() {
        return this.eventType;
    }

    public EventParam[] getParams() {
        return this.eventParamps;
    }

    public void setParams(EventParam... eventParams) {
        this.eventParamps = eventParams;
    }

}
