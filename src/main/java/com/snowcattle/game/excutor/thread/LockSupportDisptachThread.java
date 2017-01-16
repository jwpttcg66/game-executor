package com.snowcattle.game.excutor.thread;

import com.snowcattle.game.excutor.event.Event;
import com.snowcattle.game.excutor.event.EventBus;

import java.util.concurrent.locks.LockSupport;

/**
 * Created by jiangwenping on 17/1/9.
 * 带预置锁的分配器
 */
public class LockSupportDisptachThread extends DispatchThread{

    private EventBus updateServiceEventBus;
    private int cycleSize;

    private boolean runningFlag = true;

    public LockSupportDisptachThread(EventBus eventBus) {
        super(eventBus);
    }
    public void run() {
        int maxSize = 10000;
        while (runningFlag) {
            getEventBus().cycle(maxSize);
            LockSupport.park();
        }
    }

    public void addUpdateEvent(Event event){
        getEventBus().addEvent(event);
        unpark();
    }

    public void unpark(){
        LockSupport.unpark(this);
    }

    public EventBus getUpdateServiceEventBus() {
        return updateServiceEventBus;
    }

    public void setUpdateServiceEventBus(EventBus updateServiceEventBus) {
        this.updateServiceEventBus = updateServiceEventBus;
    }

    public int getCycleSize() {
        return cycleSize;
    }

    public void setCycleSize(int cycleSize) {
        this.cycleSize = cycleSize;
    }

    public boolean isRunningFlag() {
        return runningFlag;
    }

    public void setRunningFlag(boolean runningFlag) {
        this.runningFlag = runningFlag;
    }
}
