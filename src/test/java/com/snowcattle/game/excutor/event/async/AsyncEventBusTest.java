package com.snowcattle.game.excutor.event.async;

/**
 * Created by jiangwenping on 17/1/12.
 */

import com.snowcattle.game.excutor.event.EventBus;
import com.snowcattle.game.excutor.event.EventParam;
import com.snowcattle.game.excutor.event.impl.CreateEventListener;
import com.snowcattle.game.excutor.event.impl.FinishEventListener;
import com.snowcattle.game.excutor.event.impl.UpdateEventListener;
import com.snowcattle.game.excutor.thread.LockSupportDisptachThread;
import com.snowcattle.game.excutor.utils.Constants;

/**
 * Created by jiangwenping on 17/1/9.
 */
public class AsyncEventBusTest {
    public static void main(String[] args) {
        testEvent();
    }

    public static void testEvent(){
        EventBus eventBus = new EventBus();
        LockSupportDisptachThread dispatchThread = new LockSupportDisptachThread(eventBus);
        eventBus.addEventListener(new CreateEventListener());
        eventBus.addEventListener(new UpdateEventListener());
        eventBus.addEventListener(new FinishEventListener());
        //测试10万就够了
        long maxSize = 100000;
        dispatchThread.start();
        for(long i = 0; i < maxSize; i++) {
            EventParam<Integer> intParam = new EventParam<Integer>((int) i);
            EventParam<Float> floatEventParam = new EventParam<Float>((float)(i+1));
            TestCreateEvent event = new TestCreateEvent(Constants.EventTypeConstans.finishEventType, intParam, floatEventParam);
            dispatchThread.addEvent(event);
        }
    }
}