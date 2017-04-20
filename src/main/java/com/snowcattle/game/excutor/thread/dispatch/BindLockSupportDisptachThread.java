package com.snowcattle.game.excutor.thread.dispatch;

import com.snowcattle.game.excutor.event.EventBus;
import com.snowcattle.game.excutor.pool.UpdateBindExcutorService;

/**
 * Created by jwp on 2017/2/23.
 */
public class BindLockSupportDisptachThread extends LockSupportDisptachThread{

    public BindLockSupportDisptachThread(EventBus eventBus, UpdateBindExcutorService updateBindExcutorService
            , int cycleSleepTime, long minCycleTime) {
        super(eventBus, updateBindExcutorService, cycleSleepTime, minCycleTime);
    }

    @Override
    public void finishSingleUpdate(){

    }

}
