package com.snowcattle.game.executor.event.service;

import com.snowcattle.game.executor.event.EventBus;
import com.snowcattle.game.executor.event.common.IEvent;
import com.snowcattle.game.thread.executor.NonOrderedQueuePoolExecutor;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * Created by jwp on 2017/4/27.
 * 异步事件服务
 */
public class AsyncEventService {

    private EventBus eventBus;

    private BlockingQueue<IEvent> queue;

    private NonOrderedQueuePoolExecutor orderedQueuePoolExecutor;
    public AsyncEventService(EventBus eventBus, int queueSize, int workSize, String threadFactoryName) {
        this.eventBus = eventBus;
        queue = new ArrayBlockingQueue<IEvent>(queueSize);
        orderedQueuePoolExecutor = new NonOrderedQueuePoolExecutor(threadFactoryName, workSize);
    }

    public void startUp(){

    }

    public void shutDown(){

    }
}
