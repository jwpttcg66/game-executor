package com.snowcattle.game.excutor.thread;

import com.snowcattle.game.excutor.event.Event;
import com.snowcattle.game.excutor.event.EventBus;

/**
 * Created by jiangwenping on 17/1/9.
 * ⌚事件分配器
 */
public class DispatchThread extends Thread{

    private EventBus eventBus;

    public DispatchThread(EventBus eventBus) {
        this.eventBus = eventBus;
    }

    public void run() {
        eventBus.handleEvent();
    }

    public EventBus getEventBus() {
        return eventBus;
    }

    public void setEventBus(EventBus eventBus) {
        this.eventBus = eventBus;
    }

    public void shutDown(){

    }

    public void addUpdateEvent(Event event){
        getEventBus().addEvent(event);
    }
}
