package com.snowcattle.game.excutor.event;

import com.snowcattle.game.excutor.Constants;
import com.snowcattle.game.excutor.event.impl.CreateEventListener;
import com.snowcattle.game.excutor.event.impl.FinishEventListener;
import com.snowcattle.game.excutor.event.impl.UpdateEventListener;
import com.snowcattle.game.excutor.thread.LockSupportDisptachThread;

/**
 * Created by jiangwenping on 17/1/9.
 */
public class EventBusTest {
    public static void main(String[] args) {
        EventBus eventBus = new EventBus();
        LockSupportDisptachThread dispatchThread = new LockSupportDisptachThread(eventBus);
        eventBus.addEventListener(new CreateEventListener());
        eventBus.addEventListener(new UpdateEventListener());
        eventBus.addEventListener(new FinishEventListener());
        int maxSize = 1000;
        for(int i = 0; i < 10000; i++) {
            EventParam<Integer> intParam = new EventParam<Integer>(1);
            EventParam<Float> floatEventParam = new EventParam<Float>(2.0f);
            CreateEvent createEvent = new CreateEvent(Constants.EventTypeConstans.createEventType, intParam, floatEventParam);
            eventBus.addEvent(createEvent);
        }

        eventBus.handleEvent();
    }
}
