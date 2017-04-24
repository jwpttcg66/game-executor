package com.snowcattle.game.excutor.update.async;

/**
 * Created by jiangwenping on 17/1/12.
 */

import com.snowcattle.game.excutor.event.CycleEvent;
import com.snowcattle.game.excutor.event.EventBus;
import com.snowcattle.game.excutor.event.EventParam;
import com.snowcattle.game.excutor.event.impl.listener.DispatchCreateEventListener;
import com.snowcattle.game.excutor.event.impl.listener.DispatchFinishEventListener;
import com.snowcattle.game.excutor.event.impl.listener.DispatchUpdateEventListener;
import com.snowcattle.game.excutor.pool.UpdateExecutorService;
import com.snowcattle.game.excutor.service.UpdateService;
import com.snowcattle.game.excutor.thread.dispatch.LockSupportDisptachThread;
import com.snowcattle.game.excutor.utils.Constants;

/**
 * Created by jiangwenping on 17/1/9.
 */
public class AsyncUpdateBusTest {
    public static void main(String[] args) throws Exception {
        testEvent();
    }

    public static void testEvent() throws Exception {
//        EventBus eventBus = new EventBus();
        EventBus updateEventBus = new EventBus();
//        int maxSize = 10000;
//        int corePoolSize = 100;
        int maxSize = 2;
        int corePoolSize = 50;
        UpdateExecutorService updateExecutorService = new UpdateExecutorService(corePoolSize);
        int cycleSleepTime = 1000 / Constants.cycle.cycleSize;
        LockSupportDisptachThread dispatchThread = new LockSupportDisptachThread(updateEventBus, updateExecutorService
                , cycleSleepTime, cycleSleepTime * 1000000);
        UpdateService updateService = new UpdateService(dispatchThread, updateExecutorService);
        updateEventBus.addEventListener(new DispatchCreateEventListener(dispatchThread, updateService));
        updateEventBus.addEventListener(new DispatchUpdateEventListener(dispatchThread, updateService));
        updateEventBus.addEventListener(new DispatchFinishEventListener(dispatchThread, updateService));

//        updateEventBus.addEventListener(new ReadyCreateEventListener());
//        updateEventBus.addEventListener(new ReadyFinishEventListener());

//        dispatchThread.start();
        updateService.start();
        long updateMaxSize = 100;
        for (long i = 0; i < maxSize; i++) {
            IntegerUpdate integerUpdate = new IntegerUpdate(i, updateMaxSize);
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