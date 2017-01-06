package com.snowcattle.game.excutor.event;

import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * Created by jiangwenping on 17/1/6.
 */
public class EventListener implements  IEventListener{

    private Set<EventType> set;

    public EventListener() {
        this.set = new CopyOnWriteArraySet<EventType>();
    }

    public void register(EventType eventType) {
        this.set.add(eventType);
    }

    public void unRegister(EventType eventType) {
        this.set.remove(eventType);
    }

    public void fireEvent(IEvent event) {
        if(set.contains(event)){
            event.call();
        }
    }
}
