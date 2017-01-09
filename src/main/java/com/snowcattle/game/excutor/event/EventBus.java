package com.snowcattle.game.excutor.event;

import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ConcurrentSkipListSet;

/**
 * Created by jiangwenping on 17/1/9.
 */
public class EventBus implements IEventBus{

    private Set<EventListener> listenerSet;

    private Queue<IEvent> events;

    public EventBus() {
        this.listenerSet = new ConcurrentSkipListSet<EventListener>();
        this.events = new ConcurrentLinkedQueue<IEvent>();
    }

    public void addEventListener(EventListener listener) {
        if(!listenerSet.contains(listener)){
            listenerSet.add(listener);
        }
    }

    public void removeEventListener(EventListener eventListener)   {
        listenerSet.remove(eventListener);
    }

    public void clearEventListener() {
        listenerSet.clear();
    }

    public void addEvent(IEvent event) {
        this.events.add(event);
    }

    public void handleEvent() {
        while (!events.isEmpty()){
            IEvent event = events.poll();
            if(event == null){
                break;
            }
            handleSingleEvent(event);
        }
    }

    public void handleSingleEvent(IEvent event) {
        for(IEventListener eventListener:this.listenerSet){
            if(eventListener.containEventType(event.getEventType())) {
                eventListener.fireEvent(event);
            }
        }
    }

    public void clearEvent() {
        events.clear();
    }

    public void clear() {
        clearEvent();
        clearEventListener();
    }
}
