package com.snowcattle.game.excutor.thread;

import com.snowcattle.game.excutor.event.EventBus;
import com.snowcattle.game.excutor.pool.IUpdateExcutor;
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
    private IUpdateExcutor iUpdateExcutor;

    private int cycleSleepTime;
    private long minCycleTime;
    public LockSupportDisptachThread(EventBus eventBus, IUpdateExcutor iUpdateExcutor
            , int cycleSleepTime , long minCycleTime) {
        super(eventBus);
        this.iUpdateExcutor = iUpdateExcutor;
        this.cycleSleepTime = cycleSleepTime;
        this.minCycleTime = minCycleTime;
    }

    @Override
    public void run() {
        while (runningFlag) {
           singleCycle();
        }
    }

    private void singleCycle(){
        long time = System.nanoTime();
        int cycleSize = getEventBus().getEventsSize();
        int size = getEventBus().cycle(cycleSize);
        LockSupport.park();

        long notifyTime = System.nanoTime();
        long diff = (int) (notifyTime - time);
        if(diff < minCycleTime &&  diff > 0){
            try {
                Thread.currentThread().sleep(cycleSleepTime, (int) (diff%999999));
            } catch (Throwable e) {
                Loggers.utilLogger.error(e.toString(), e);
            }
        }
    }
    @Override
    public void notifyRun() {
       singleCycle();
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

    public boolean isRunningFlag() {
        return runningFlag;
    }

    public void setRunningFlag(boolean runningFlag) {
        this.runningFlag = runningFlag;
    }

    public IUpdateExcutor getiUpdateExcutor() {
        return iUpdateExcutor;
    }

    public void setiUpdateExcutor(IUpdateExcutor iUpdateExcutor) {
        this.iUpdateExcutor = iUpdateExcutor;
    }

    public void shutDown(){
        this.runningFlag = false;
        super.shutDown();
    }
}
