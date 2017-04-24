package com.snowcattle.game.excutor.thread.dispatch;

import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.YieldingWaitStrategy;
import com.snowcattle.game.excutor.event.CycleEvent;
import com.snowcattle.game.excutor.event.common.IEvent;
import com.snowcattle.game.excutor.event.common.constant.EventTypeEnum;
import com.snowcattle.game.excutor.event.factory.CycleDisruptorEventFactory;
import com.snowcattle.game.excutor.pool.DisruptorExcutorService;
import com.snowcattle.game.excutor.pool.IUpdateExcutor;

/**
 * Created by jiangwenping on 17/4/24.
 * 加入disruptor
 */
public class DisruptorDispatchThread extends DispatchThread{

    private RingBuffer<CycleEvent> ringBuffer;

    private int bufferSize = Short.MAX_VALUE * EventTypeEnum.values().length;

    private DisruptorExcutorService disruptorExcutorService;

    public DisruptorDispatchThread(IUpdateExcutor iUpdateExcutor) {
        super(null);
        this.disruptorExcutorService = (DisruptorExcutorService) iUpdateExcutor;
    }

    public void initRingBuffer(){
        ringBuffer = RingBuffer.createSingleProducer(new CycleDisruptorEventFactory(), bufferSize, new YieldingWaitStrategy());
    }

    public void initWorkerPool(){

    }
    public void addUpdateEvent(IEvent event){
        dispatch(event);
    }
    public void addCreateEvent(IEvent event){
        dispatch(event);
    }

    public void addFinishEvent(IEvent event){
        dispatch(event);
    }

    public void shutDown(){

    }


    public void dispatch(IEvent event){
        ringBuffer = disruptorExcutorService.getDispatchRingBuffer();
        long next = ringBuffer.next();
        ringBuffer.publish(next);
    }

    public RingBuffer<CycleEvent> getRingBuffer() {
        return ringBuffer;
    }

    public void setRingBuffer(RingBuffer<CycleEvent> ringBuffer) {
        this.ringBuffer = ringBuffer;
    }

    public DisruptorExcutorService getDisruptorExcutorService() {
        return disruptorExcutorService;
    }

    public void setDisruptorExcutorService(DisruptorExcutorService disruptorExcutorService) {
        this.disruptorExcutorService = disruptorExcutorService;
    }


}

