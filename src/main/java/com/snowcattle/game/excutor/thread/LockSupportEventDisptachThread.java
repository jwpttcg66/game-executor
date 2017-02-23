package com.snowcattle.game.excutor.thread;

import com.snowcattle.game.excutor.event.EventBus;
import com.snowcattle.game.excutor.pool.UpdateEventExcutorService;
import com.snowcattle.game.excutor.pool.UpdateExecutorService;
import com.snowcattle.game.excutor.utils.Loggers;

import java.util.concurrent.locks.LockSupport;

/**
 * Created by jwp on 2017/2/23.
 */
public class LockSupportEventDisptachThread extends LockSupportDisptachThread{

    public LockSupportEventDisptachThread(EventBus eventBus, UpdateEventExcutorService updateEventExcutorService
            , int cycleTime , long minCycleTime) {
        super(eventBus,updateEventExcutorService, cycleTime, minCycleTime);
    }

}
