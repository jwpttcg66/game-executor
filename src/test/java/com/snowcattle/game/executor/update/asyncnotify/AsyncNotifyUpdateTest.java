package com.snowcattle.game.executor.update.asyncnotify;

import com.snowcattle.game.executor.event.CycleEvent;
import com.snowcattle.game.executor.event.EventBus;
import com.snowcattle.game.executor.event.EventParam;
import com.snowcattle.game.executor.update.async.IntegerUpdate;
import com.snowcattle.game.executor.event.impl.listener.DispatchCreateEventListener;
import com.snowcattle.game.executor.event.impl.listener.DispatchFinishEventListener;
import com.snowcattle.game.executor.event.impl.listener.DispatchUpdateEventListener;
import com.snowcattle.game.executor.update.pool.UpdateBindExcutorService;
import com.snowcattle.game.executor.update.service.UpdateService;
import com.snowcattle.game.executor.update.thread.dispatch.BindDisptachThread;
import com.snowcattle.game.executor.common.utils.Constants;

import java.util.Timer;
import java.util.concurrent.TimeUnit;

/**
 * Created by jwp on 2017/3/28.
 */
public class AsyncNotifyUpdateTest {
    public static void main(String[] args) throws Exception {
        testUpdate();
    }

    public static void testUpdate() throws Exception {
        EventBus updateEventBus = new EventBus();
//        int maxSize = 10000;
//        int corePoolSize = 100;
        int maxSize = 1;
        int corePoolSize = 10;
        long keepAliveTime = 60;
        TimeUnit timeUnit = TimeUnit.SECONDS;
        UpdateBindExcutorService updateBindExcutorService = new UpdateBindExcutorService(corePoolSize);
        int cycleSleepTime = 1000 / Constants.cycle.cycleSize;
        BindDisptachThread dispatchThread = new BindDisptachThread(updateEventBus, updateBindExcutorService
                , cycleSleepTime, cycleSleepTime*1000);
        updateBindExcutorService.setDispatchThread(dispatchThread);
        UpdateService updateService = new UpdateService(dispatchThread, updateBindExcutorService);
        updateEventBus.addEventListener(new DispatchCreateEventListener(dispatchThread, updateService));
        updateEventBus.addEventListener(new DispatchUpdateEventListener(dispatchThread, updateService));
        updateEventBus.addEventListener(new DispatchFinishEventListener(dispatchThread, updateService));

        updateService.notifyStart();

        long updateMaxSize = 100;
        for (long i = 0; i < maxSize; i++) {
            IntegerUpdate integerUpdate = new IntegerUpdate(i, updateMaxSize);
            EventParam<IntegerUpdate> param = new EventParam<IntegerUpdate>(integerUpdate);
            CycleEvent cycleEvent = new CycleEvent(Constants.EventTypeConstans.readyCreateEventType, integerUpdate.getId(), param);
            updateService.addReadyCreateEvent(cycleEvent);
        }


//        updateService.shutDown();
        Timer timer = new Timer();
        timer.schedule(new NotifyTask(updateService), 0, 10);
        while (true) {
            Thread.currentThread().sleep(100);
            updateService.toString();
        }
    }
}

