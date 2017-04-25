package com.snowcattle.game.excutor.update.disruptor;

import com.snowcattle.game.excutor.event.CycleEvent;
import com.snowcattle.game.excutor.event.EventBus;
import com.snowcattle.game.excutor.event.EventParam;
import com.snowcattle.game.excutor.event.impl.listener.DispatchCreateEventListener;
import com.snowcattle.game.excutor.event.impl.listener.DispatchFinishEventListener;
import com.snowcattle.game.excutor.event.impl.listener.DispatchUpdateEventListener;
import com.snowcattle.game.excutor.pool.DisruptorExcutorService;
import com.snowcattle.game.excutor.service.UpdateService;
import com.snowcattle.game.excutor.thread.dispatch.DisruptorDispatchThread;
import com.snowcattle.game.excutor.update.async.IntegerUpdate;
import com.snowcattle.game.excutor.utils.Constants;

import java.util.concurrent.TimeUnit;

/**
 * Created by jiangwenping on 17/4/25.
 */
public class DisruptorTest {
    public static void main(String[] args) throws Exception {
        testUpdate();
    }

    public static void testUpdate() throws Exception {
        EventBus updateEventBus = new EventBus();
//        int maxSize = 10000;
//        int corePoolSize = 100;
        int maxSize = 200;
        int corePoolSize = 20;
        long keepAliveTime = 60;
        TimeUnit timeUnit = TimeUnit.SECONDS;
        DisruptorExcutorService disruptorExcutorService = new DisruptorExcutorService(corePoolSize);
        int cycleSleepTime = 1000 / Constants.cycle.cycleSize;
//        DisruptorDispatchThread dispatchThread = new DisruptorDispatchThread(updateEventBus, disruptorExcutorService
//                , cycleSleepTime, cycleSleepTime*1000);
        DisruptorDispatchThread dispatchThread = new DisruptorDispatchThread(updateEventBus, disruptorExcutorService);
        disruptorExcutorService.setDisruptorDispatchThread(dispatchThread);
        UpdateService updateService = new UpdateService(dispatchThread, disruptorExcutorService);

        updateEventBus.addEventListener(new DispatchCreateEventListener(dispatchThread, updateService));
        updateEventBus.addEventListener(new DispatchUpdateEventListener(dispatchThread, updateService));
        updateEventBus.addEventListener(new DispatchFinishEventListener(dispatchThread, updateService));

//        updateService.notifyStart();
        updateService.start();
        long updateMaxSize = 100;
        for (long i = 0; i < maxSize; i++) {
            IntegerUpdate integerUpdate = new IntegerUpdate(i, updateMaxSize);
            EventParam<IntegerUpdate> param = new EventParam<IntegerUpdate>(integerUpdate);
            CycleEvent cycleEvent = new CycleEvent(Constants.EventTypeConstans.readyCreateEventType, integerUpdate.getId(), param);
            updateService.addReadyCreateEvent(cycleEvent);
        }


        while (true) {
            Thread.currentThread().sleep(100);
            updateService.toString();
        }
    }
}
