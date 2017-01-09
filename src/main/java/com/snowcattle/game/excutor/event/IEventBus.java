package com.snowcattle.game.excutor.event;

/**
 * Created by jiangwenping on 17/1/6.
 * ⌚
 */
public interface IEventBus {
    public void addEventListener(EventListener listene);
    public void removeEventListener(EventListener listene);
    public void clearEventListener();
    public void addEvent(IEvent event);
    public void handleEvent();
    public void clearEvent();
    public void clear();
}
