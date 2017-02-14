package com.snowcattle.game.excutor.thread;

import com.snowcattle.game.excutor.event.EventBus;
import com.snowcattle.game.excutor.pool.UpdateExecutorService;
import com.snowcattle.game.excutor.utils.Loggers;

import java.util.concurrent.locks.LockSupport;

/**
 * Created by jiangwenping on 17/1/9.
 * 带预置锁的分配器
 *  接受create, update, finish事件
 *   负责整个调度器的调度 ,按照bus里面的大小来确定每次循环多少个
 */
public class LockSupportDisptachThread extends DispatchThread{

    private EventBus updateServiceEventBus;

    private boolean runningFlag = true;
    private UpdateExecutorService updateExecutorService;

    private int cycleTime;
    private long minCycleTime;
    public LockSupportDisptachThread(EventBus eventBus, EventBus updateServiceEventBus, UpdateExecutorService updateExecutorService
            , int cycleTime , long minCycleTime) {
        super(eventBus);
        this.updateServiceEventBus = updateServiceEventBus;
        this.updateExecutorService = updateExecutorService;
        this.cycleTime = cycleTime;
        this.minCycleTime = minCycleTime;
    }

    public void run() {
        while (runningFlag) {
            long time = System.nanoTime();
            int cycleSize = getEventBus().getEventsSize();
            int size = getEventBus().cycle(cycleSize);
            LockSupport.park();

            long notifyTime = System.nanoTime();
            int diff = (int) (notifyTime - time);
//            int cycleTime = 1000 / Constants.cycle.cycleSize;
//            long minTime = 1000 * cycleTime;
            if(diff < minCycleTime){
                try {
                    Thread.currentThread().sleep(cycleTime, diff%999999);
                } catch (Exception e) {
                    Loggers.utilLogger.error(e.toString(), e);
                }
            }

        }
    }

//    public void addUpdateEvent(Event event){
//        getEventBus().addEvent(event);
//        unpark();
//    }

    public void unpark(){
        LockSupport.unpark(this);
    }

    public EventBus getUpdateServiceEventBus() {
        return updateServiceEventBus;
    }

    public void setUpdateServiceEventBus(EventBus updateServiceEventBus) {
        this.updateServiceEventBus = updateServiceEventBus;
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

    public void shutDown(){
        this.runningFlag = false;
        super.shutDown();
    }
}
