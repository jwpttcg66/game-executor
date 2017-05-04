package com.snowcattle.game.executor.event.service;

import com.snowcattle.game.executor.event.EventBus;
import com.snowcattle.game.executor.event.common.IEvent;
import com.snowcattle.game.thread.executor.OrderedQueuePoolExecutor;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * Created by jwp on 2017/4/27.
 * 异步事件服务
 */
public class AsyncEventService {

    private EventBus eventBus;

    private BlockingQueue<IEvent> queue;

    private OrderedQueuePoolExecutor orderedQueuePoolExecutor;

    /**
     *
     * @param eventBus
     * @param queueSize 生产者队列大小
     * @param workSize  消费者工作线程
     * @param threadFactoryName 执行线程池名字
     * @param orderQueueMaxSize  顺序执行线程池队列大小
     */
    public AsyncEventService(EventBus eventBus, int queueSize, int workSize, String threadFactoryName, int orderQueueMaxSize) {
        this.eventBus = eventBus;
        queue = new ArrayBlockingQueue<IEvent>(queueSize);
        orderedQueuePoolExecutor = new OrderedQueuePoolExecutor(threadFactoryName, workSize, orderQueueMaxSize);
    }

    public void startUp(){

    }

    public void shutDown(){

    }
}
