package com.snowcattle.game.excutor.pool;

import com.lmax.disruptor.FatalExceptionHandler;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.WorkerPool;
import com.snowcattle.game.excutor.entity.IUpdate;
import com.snowcattle.game.excutor.event.handler.CycleEventHandler;
import com.snowcattle.game.excutor.thread.dispatch.DispatchThread;
import com.snowcattle.game.excutor.thread.dispatch.DisruptorDispatchThread;

/**
 * Created by jiangwenping on 17/4/24.
 */
public class DisruptorExcutorService implements IUpdateExcutor {

    private WorkerPool workerPool;

    private int excutorSize;

    private CycleEventHandler[] cycleEventHandler;

    private DisruptorDispatchThread disruptorDispatchThread ;

    public DisruptorExcutorService(int excutorSize) {
        this.excutorSize = excutorSize;
    }

    @Override
    public void excutorUpdate(DispatchThread dispatchThread, IUpdate iUpdate, boolean firstFlag, int updateExcutorIndex) {

    }

    @Override
    public void start() {
        cycleEventHandler = new CycleEventHandler[excutorSize];
        RingBuffer ringBuffer = disruptorDispatchThread.getRingBuffer();
        workerPool = new WorkerPool(ringBuffer, ringBuffer.newBarrier(), new FatalExceptionHandler(), cycleEventHandler);
        ringBuffer.addGatingSequences(workerPool.getWorkerSequences());
    }

    @Override
    public void stop() {
        workerPool.drainAndHalt();
    }

    public DisruptorDispatchThread getDisruptorDispatchThread() {
        return disruptorDispatchThread;
    }

    public void setDisruptorDispatchThread(DisruptorDispatchThread disruptorDispatchThread) {
        this.disruptorDispatchThread = disruptorDispatchThread;
    }

}
