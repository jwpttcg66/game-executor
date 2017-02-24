package com.snowcattle.game.excutor.event.async;

/**
 * Created by jiangwenping on 17/1/12.
 */

import com.snowcattle.game.excutor.event.CycleEvent;
import com.snowcattle.game.excutor.event.EventBus;
import com.snowcattle.game.excutor.event.EventParam;
import com.snowcattle.game.excutor.event.impl.*;
import com.snowcattle.game.excutor.pool.UpdateExecutorService;
import com.snowcattle.game.excutor.service.UpdateService;
import com.snowcattle.game.excutor.thread.LockSupportDisptachThread;
import com.snowcattle.game.excutor.utils.Constants;

import java.util.concurrent.TimeUnit;

/**
 * Created by jiangwenping on 17/1/9.
 */
public class AsyncEventBusTest {
    public static void main(String[] args) throws Exception {
        testEvent();
    }

    public static void testEvent() throws Exception {
//        EventBus eventBus = new EventBus();
        EventBus updateEventBus = new EventBus();
//        int maxSize = 10000;
//        int corePoolSize = 100;
        int maxSize = 1;
        int corePoolSize = 1;
        long keepAliveTime = 60;
        TimeUnit timeUnit = TimeUnit.SECONDS;
        UpdateExecutorService updateExecutorService = new UpdateExecutorService(corePoolSize, keepAliveTime, timeUnit);
        int cycleSleepTime = 1000 / Constants.cycle.cycleSize;
        LockSupportDisptachThread dispatchThread = new LockSupportDisptachThread(updateEventBus, updateExecutorService
                , cycleSleepTime, cycleSleepTime * 1000);
        UpdateService updateService = new UpdateService(dispatchThread, updateExecutorService);
        updateEventBus.addEventListener(new DispatchCreateEventListener(dispatchThread, updateService));
        updateEventBus.addEventListener(new DispatchUpdateEventListener(dispatchThread, updateService));
        updateEventBus.addEventListener(new DispatchFinishEventListener(dispatchThread, updateService));

//        updateEventBus.addEventListener(new ReadyCreateEventListener());
//        updateEventBus.addEventListener(new ReadyFinishEventListener());

//        dispatchThread.start();
        updateService.start();
        for (long i = 0; i < maxSize; i++) {
            IntegerUpdate integerUpdate = new IntegerUpdate(i);
            EventParam<IntegerUpdate> param = new EventParam<IntegerUpdate>(integerUpdate);
            CycleEvent cycleEvent = new CycleEvent(Constants.EventTypeConstans.readyCreateEventType, integerUpdate.getId(), param);
            updateService.addReadyCreateEvent(cycleEvent);
        }

//        while (true){
//            Thread.currentThread().sleep(100);
//            updateService.toString();
//        }
//        updateService.shutDown();
        while (true) {
            Thread.currentThread().sleep(100);
            updateService.toString();
        }

    }
}