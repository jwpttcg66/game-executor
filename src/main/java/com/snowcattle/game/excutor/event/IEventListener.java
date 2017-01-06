package com.snowcattle.game.excutor.event;

/**
 * Created by jiangwenping on 17/1/6.
 * 事件监听器
 */
public interface IEventListener {

    public void register(EventType eventType);
    public void unRegister(EventType eventType);
    public void fireEvent(IEvent event);
}
