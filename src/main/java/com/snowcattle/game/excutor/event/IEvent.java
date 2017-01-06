package com.snowcattle.game.excutor.event;

/**
 * Created by jiangwenping on 17/1/6.
 * 事件定义
 */
public interface IEvent {
    public EventType getEventType();
    public EventParam[] getParams();
    public void call();
}
