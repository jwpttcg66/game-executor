package com.snowcattle.game.excutor.thread.dispatch;

import com.snowcattle.game.excutor.event.EventBus;
import com.snowcattle.game.excutor.pool.UpdateEventExcutorService;

/**
 * Created by jwp on 2017/2/23.
 */
public class LockSupportEventDisptachThread extends LockSupportDisptachThread{

    public LockSupportEventDisptachThread(EventBus eventBus, UpdateEventExcutorService updateEventExcutorService
            , int cycleSleepTime , long minCycleTime) {
        super(eventBus,updateEventExcutorService, cycleSleepTime, minCycleTime);
    }

}
