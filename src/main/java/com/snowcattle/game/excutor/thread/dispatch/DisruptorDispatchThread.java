package com.snowcattle.game.excutor.thread.dispatch;

import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.YieldingWaitStrategy;
import com.snowcattle.game.excutor.event.CycleEvent;
import com.snowcattle.game.excutor.event.common.IEvent;
import com.snowcattle.game.excutor.event.common.constant.EventTypeEnum;
import com.snowcattle.game.excutor.event.factory.CycleDisruptorEventFactory;

/**
 * Created by jiangwenping on 17/4/24.
 * 加入disruptor
 */
public class DisruptorDispatchThread extends DispatchThread{

    private RingBuffer<CycleEvent> ringBuffer;

    private int bufferSize = Short.MAX_VALUE * EventTypeEnum.values().length;

    public DisruptorDispatchThread() {
        super(null);

    }

    public void initRingBuffer(){
        ringBuffer = RingBuffer.createSingleProducer(new CycleDisruptorEventFactory(), bufferSize, new YieldingWaitStrategy());
    }

    public void initWorkerPool(){

    }
    public void addUpdateEvent(IEvent event){
        dispatch();
    }
    public void addCreateEvent(IEvent event){
        dispatch();
    }

    public void addFinishEvent(IEvent event){
        dispatch();
    }

    public void shutDown(){

    }


    public void dispatch(){
        long next = ringBuffer.next();
        ringBuffer.publish(next);
    }

    public RingBuffer<CycleEvent> getRingBuffer() {
        return ringBuffer;
    }

    public void setRingBuffer(RingBuffer<CycleEvent> ringBuffer) {
        this.ringBuffer = ringBuffer;
    }
}

