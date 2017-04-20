package com.snowcattle.game.excutor.thread.dispatch;

import com.snowcattle.game.excutor.event.impl.EventBus;
import com.snowcattle.game.excutor.event.common.IEvent;
import com.snowcattle.game.excutor.utils.Constants;

import java.util.concurrent.locks.LockSupport;

/**
 * Created by jiangwenping on 17/1/9.
 * ⌚事件分配器
 */
public class DispatchThread extends Thread{

    private EventBus eventBus;

    public DispatchThread(EventBus eventBus) {
        super(Constants.Thread.DISPATCH);
        this.eventBus = eventBus;
    }

    public void run() {
        eventBus.handleEvent();
    }

    public void notifyRun(){
        eventBus.handleEvent();
    }

    public EventBus getEventBus() {
        return eventBus;
    }

    public void setEventBus(EventBus eventBus) {
        this.eventBus = eventBus;
    }

    public void shutDown(){
        eventBus.clear();
    }

    public void addUpdateEvent(IEvent event){
        getEventBus().addEvent(event);
    }
    public void addCreateEvent(IEvent event){
        getEventBus().addEvent(event);
    }

    public void addFinishEvent(IEvent event){
        getEventBus().addEvent(event);
    }

    public void unpark(){
        LockSupport.unpark(this);
    }
    public void park(){
        LockSupport.park(this);
    }
}
