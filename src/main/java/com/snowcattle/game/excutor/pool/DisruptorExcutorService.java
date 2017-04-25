package com.snowcattle.game.excutor.pool;

import com.lmax.disruptor.*;
import com.snowcattle.game.excutor.entity.IUpdate;
import com.snowcattle.game.excutor.event.CycleEvent;
import com.snowcattle.game.excutor.event.EventBus;
import com.snowcattle.game.excutor.event.common.IEvent;
import com.snowcattle.game.excutor.event.handler.CycleEventHandler;
import com.snowcattle.game.excutor.thread.dispatch.DispatchThread;
import com.snowcattle.game.excutor.thread.dispatch.DisruptorDispatchThread;
import com.snowcattle.game.thread.executor.NonOrderedQueuePoolExecutor;

import java.util.concurrent.ExecutorService;

/**
 * Created by jiangwenping on 17/4/24.
 */
public class DisruptorExcutorService implements IUpdateExcutor {

    private WorkerPool workerPool;

    private int excutorSize;

    private CycleEventHandler[] cycleEventHandler;

    private DisruptorDispatchThread disruptorDispatchThread ;

    private ExecutorService executorService;

    public DisruptorExcutorService(int excutorSize) {
        this.excutorSize = excutorSize;
    }

    @Override
    public void excutorUpdate(DispatchThread dispatchThread, IUpdate iUpdate, boolean firstFlag, int updateExcutorIndex) {

    }

    @Override
    public void startup() {
        EventBus eventBus = disruptorDispatchThread.getEventBus();
        executorService = new NonOrderedQueuePoolExecutor(excutorSize);
        cycleEventHandler = new CycleEventHandler[excutorSize];
        for(int i = 0; i < excutorSize; i++){
            cycleEventHandler[i] = new CycleEventHandler(eventBus);
        }

        RingBuffer ringBuffer = disruptorDispatchThread.getRingBuffer();
        SequenceBarrier sequenceBarrier = ringBuffer.newBarrier();
        BatchEventProcessor<CycleEvent>[] batchEventProcessors = new BatchEventProcessor[excutorSize];
        for(int i = 0; i < excutorSize; i++){
            batchEventProcessors[i] = new BatchEventProcessor<>(ringBuffer, sequenceBarrier, cycleEventHandler[i]);
            ringBuffer.addGatingSequences(batchEventProcessors[i].getSequence());
            executorService.submit(batchEventProcessors[i]);
        }
    }

    @Override
    public void shutdown() {
        workerPool.drainAndHalt();
    }

    public DisruptorDispatchThread getDisruptorDispatchThread() {
        return disruptorDispatchThread;
    }

    public void setDisruptorDispatchThread(DisruptorDispatchThread disruptorDispatchThread) {
        this.disruptorDispatchThread = disruptorDispatchThread;
    }

    public WorkerPool getWorkerPool() {
        return workerPool;
    }

    public void setWorkerPool(WorkerPool workerPool) {
        this.workerPool = workerPool;
    }

    public RingBuffer getDispatchRingBuffer(){
        return disruptorDispatchThread.getRingBuffer();
    }

    public void dispatch(IEvent event){
        RingBuffer ringBuffer = getDispatchRingBuffer();
        long next = ringBuffer.next();
        ringBuffer.publish(next);
    }
}
