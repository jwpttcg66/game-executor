package com.snowcattle.game.excutor.thread.dispatch;

import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.YieldingWaitStrategy;
import com.snowcattle.game.excutor.event.CycleEvent;
import com.snowcattle.game.excutor.event.EventBus;
import com.snowcattle.game.excutor.event.common.IEvent;
import com.snowcattle.game.excutor.event.factory.CycleDisruptorEventFactory;
import com.snowcattle.game.excutor.pool.DisruptorExcutorService;
import com.snowcattle.game.excutor.pool.IUpdateExcutor;

/**
 * Created by jiangwenping on 17/4/24.
 * 加入disruptor
 */
public class DisruptorDispatchThread extends DispatchThread{

    private RingBuffer<CycleEvent> ringBuffer;

    private int bufferSize = 1024 * 256;

    private DisruptorExcutorService disruptorExcutorService;

    public DisruptorDispatchThread(EventBus eventBus, IUpdateExcutor iUpdateExcutor) {
        super(eventBus);
        this.disruptorExcutorService = (DisruptorExcutorService) iUpdateExcutor;
    }

    public void initRingBuffer(){
        ringBuffer = RingBuffer.createSingleProducer(new CycleDisruptorEventFactory(), bufferSize, new YieldingWaitStrategy());
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

    @Override
    public void unpark() {

    }

    @Override
    void park() {

    }

    @Override
    public IUpdateExcutor getiUpdateExcutor() {
        return null;
    }

    @Override
    public void startup() {
        initRingBuffer();
    }

    public void shutDown(){

    }


    public void dispatch(IEvent event){
        ringBuffer = disruptorExcutorService.getDispatchRingBuffer();
        long next = ringBuffer.next();
        CycleEvent cycleEvent = (CycleEvent) event;
        ringBuffer.get(next).setId(cycleEvent.getId());
        ringBuffer.get(next).setEventType(event.getEventType());
        ringBuffer.get(next).setParams(event.getParams());
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

