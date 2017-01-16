package com.snowcattle.game.excutor.thread;

import com.snowcattle.game.excutor.event.Event;
import com.snowcattle.game.excutor.event.EventBus;
import com.snowcattle.game.excutor.pool.UpdateExecutorService;

import java.util.concurrent.locks.LockSupport;

/**
 * Created by jiangwenping on 17/1/9.
 * 带预置锁的分配器
 *  接受create, update, finish事件
 *   负责整个调度器的调度
 */
public class LockSupportDisptachThread extends DispatchThread{

    private EventBus updateServiceEventBus;
    private int cycleSize;

    private boolean runningFlag = true;
    private UpdateExecutorService updateExecutorService;

    public LockSupportDisptachThread(EventBus eventBus, EventBus updateServiceEventBus, UpdateExecutorService updateExecutorService, int cycleSize) {
        super(eventBus);
        this.updateServiceEventBus = updateServiceEventBus;
        this.updateExecutorService = updateExecutorService;
        this.cycleSize = cycleSize;
    }

    public void run() {
        while (runningFlag) {
            getEventBus().cycle(cycleSize);
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

    public UpdateExecutorService getUpdateExecutorService() {
        return updateExecutorService;
    }

    public void setUpdateExecutorService(UpdateExecutorService updateExecutorService) {
        this.updateExecutorService = updateExecutorService;
    }
}
