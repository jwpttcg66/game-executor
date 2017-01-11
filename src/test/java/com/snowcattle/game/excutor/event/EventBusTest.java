package com.snowcattle.game.excutor.event;

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
    }
}
