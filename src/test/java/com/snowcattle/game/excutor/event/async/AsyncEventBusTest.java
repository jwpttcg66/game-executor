package com.snowcattle.game.excutor.event.async;

/**
 * Created by jiangwenping on 17/1/12.
 */

import com.snowcattle.game.excutor.event.EventBus;
import com.snowcattle.game.excutor.event.impl.DispatchCreateEventListener;
import com.snowcattle.game.excutor.event.impl.ReadyCreateEventListener;
import com.snowcattle.game.excutor.event.impl.ReadyFinishEventListener;
import com.snowcattle.game.excutor.pool.UpdateExecutorService;
import com.snowcattle.game.excutor.thread.LockSupportDisptachThread;

import java.util.concurrent.TimeUnit;

/**
 * Created by jiangwenping on 17/1/9.
 */
public class AsyncEventBusTest {
    public static void main(String[] args) {
        testEvent();
    }

    public static void testEvent(){
        EventBus eventBus = new EventBus();
        EventBus updateEventBus = new EventBus();
        int maxSize = 1000;
        int corePoolSize= 100;
        long keepAliveTime = 60;
        TimeUnit timeUnit = TimeUnit.SECONDS;
        UpdateExecutorService updateExecutorService = new UpdateExecutorService(corePoolSize, keepAliveTime, timeUnit);
        LockSupportDisptachThread dispatchThread = new LockSupportDisptachThread(eventBus, updateEventBus, updateExecutorService,  maxSize);
        eventBus.addEventListener(new DispatchCreateEventListener(dispatchThread));
        eventBus.addEventListener(new DispatchCreateEventListener(dispatchThread));
        eventBus.addEventListener(new DispatchCreateEventListener(dispatchThread));

        updateEventBus.addEventListener(new ReadyCreateEventListener());
        updateEventBus.addEventListener(new ReadyFinishEventListener());

        //测试10万就够了
        dispatchThread.start();

//        for(long i = 0; i < maxSize; i++) {
//            EventParam<Integer> intParam = new EventParam<Integer>((int) i);
//            EventParam<Float> floatEventParam = new EventParam<Float>((float)(i+1));
//            TestCreateEvent event = new TestCreateEvent(Constants.EventTypeConstans.finishEventType, intParam, floatEventParam);
//            dispatchThread.addUpdateEvent(event);
//        }
    }
}