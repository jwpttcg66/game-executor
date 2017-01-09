package com.snowcattle.game.excutor;

import com.snowcattle.game.excutor.event.EventType;
import com.snowcattle.game.excutor.event.EventTypeEnum;

/**
 * Created by jiangwenping on 17/1/9.
 */
public class Constants {

    public static class EventTypeConstans{
        public static EventType createEventType = new EventType(EventTypeEnum.create.ordinal());

    }
}
