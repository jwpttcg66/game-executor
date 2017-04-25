package com.snowcattle.game.excutor.event.common;

import com.snowcattle.game.excutor.event.EventParam;
import com.snowcattle.game.excutor.event.EventType;

/**
 * Created by jiangwenping on 17/1/6.
 * 事件定义
 */
public interface IEvent{
    public void setEventType(EventType eventType);
    public EventType getEventType();
    public EventParam[] getParams();
    public void setParams(EventParam... eventParams);
    public void call();
}
