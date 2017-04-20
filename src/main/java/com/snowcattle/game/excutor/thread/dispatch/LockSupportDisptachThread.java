package com.snowcattle.game.excutor.thread.dispatch;

import com.snowcattle.game.excutor.event.EventBus;
import com.snowcattle.game.excutor.event.common.IEvent;
import com.snowcattle.game.excutor.event.common.constant.EventTypeEnum;
import com.snowcattle.game.excutor.pool.IUpdateExcutor;
import com.snowcattle.game.excutor.utils.Loggers;

import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by jiangwenping on 17/1/9.
 * 带预置锁的分配器
 *  接受create, update, finish事件
 *   负责整个调度器的调度 ,按照bus里面的大小来确定每次循环多少个
 */
public class LockSupportDisptachThread extends DispatchThread {

    private EventBus updateServiceEventBus;

    private boolean runningFlag = true;
    private IUpdateExcutor iUpdateExcutor;

    private int cycleSleepTime;
    private long minCycleTime;
    private long maxCycleCount;

    /**
     * 当前执行的update数量, 调度数量过大的时候，会创建过多的线程导致内存溢出
     */
    private AtomicLong updateCount = new AtomicLong();

    public LockSupportDisptachThread(EventBus eventBus, IUpdateExcutor iUpdateExcutor
            , int cycleSleepTime , long minCycleTime) {
        super(eventBus);
        this.iUpdateExcutor = iUpdateExcutor;
        this.cycleSleepTime = cycleSleepTime;
        this.minCycleTime = minCycleTime;
        this.maxCycleCount = Long.MAX_VALUE;
    }

    public LockSupportDisptachThread(EventBus eventBus, IUpdateExcutor iUpdateExcutor
            , int cycleSleepTime , long minCycleTime, long maxCycleCount) {
        super(eventBus);
        this.iUpdateExcutor = iUpdateExcutor;
        this.cycleSleepTime = cycleSleepTime;
        this.minCycleTime = minCycleTime;
        this.maxCycleCount = maxCycleCount;
    }

    @Override
    public void run() {
        while (runningFlag) {
           singleCycle(true);
        }
    }

    private void singleCycle(boolean sleepFlag){
        long time = System.nanoTime();
        int cycleSize = getEventBus().getEventsSize();
        if(sleepFlag) {
            int size = getEventBus().cycle(cycleSize);
            while (updateCount.get() < maxCycleCount) {
                IEvent event  = getEventBus().pollEvent();
                if(event != null) {
                    if (event.getEventType().equals(EventTypeEnum.UPDATE.ordinal())) {
                        updateCount.getAndIncrement();
                    }
                    getEventBus().handleSingleEvent(event);
                }else{
                    break;
                }

            }
            //调度计算
            park();
            long notifyTime = System.nanoTime();
            long diff = (int) (notifyTime - time);
            if (diff < minCycleTime && diff > 0) {
                try {
                    Thread.currentThread().sleep(cycleSleepTime, (int) (diff % 999999));
                } catch (Throwable e) {
                    Loggers.utilLogger.error(e.toString(), e);
                }
            }
        }else{
            int size = getEventBus().cycle(cycleSize);
        }
    }


    @Override
    public void notifyRun() {
       singleCycle(false);
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

    @Override
    public void finishSingleUpdate(){
        this.updateCount.decrementAndGet();
    }
}
