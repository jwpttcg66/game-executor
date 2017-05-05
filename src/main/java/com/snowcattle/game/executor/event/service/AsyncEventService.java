package com.snowcattle.game.executor.event.service;

import com.snowcattle.game.executor.common.utils.CommonErrorInfo;
import com.snowcattle.game.executor.common.utils.ExecutorUtil;
import com.snowcattle.game.executor.common.utils.Loggers;
import com.snowcattle.game.executor.event.EventBus;
import com.snowcattle.game.executor.event.common.IEvent;
import com.snowcattle.game.thread.executor.OrderedQueuePoolExecutor;
import org.slf4j.Logger;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * Created by jwp on 2017/4/27.
 * 异步事件服务
 */
public class AsyncEventService {

    private Logger eventLogger = Loggers.gameExcutorEvent;

    private EventBus eventBus;

    private BlockingQueue<IEvent> queue;

    private OrderedQueuePoolExecutor orderedQueuePoolExecutor;


    /** 处理的消息总数 */
    public long statisticsMessageCount = 0;

    /**work线程池大小*/
    private int workSize;
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
        this.workSize = workSize;
        orderedQueuePoolExecutor = new OrderedQueuePoolExecutor(threadFactoryName, workSize, orderQueueMaxSize);
    }

    public void startUp(){
        if (this.orderedQueuePoolExecutor != null) {
            throw new IllegalStateException(
                    "AsyncEventService The executorSerive has not been stopped.");
        }

        eventLogger.info("AsyncEventService processor executorService started ["
                + this.orderedQueuePoolExecutor + " with " + this.workSize
                + " threads ]");
    }

    public void shutDown(){

        eventLogger.info("AsyncEventService " + this + " stopping ...");
        if (this.orderedQueuePoolExecutor != null) {
            ExecutorUtil.shutdownAndAwaitTermination(this.orderedQueuePoolExecutor, 60,
                    TimeUnit.MILLISECONDS);
            this.orderedQueuePoolExecutor = null;
        }
        eventLogger.info("AsyncEventService" + this + " stopped");
    }


    /**
     * 处理具体的消息，消息
     *
     * @param event
     */
    public void put(IEvent event) {
        try {
            queue.put(event);
            if (eventLogger.isDebugEnabled()) {
                eventLogger.debug("put queue size:" + queue.size());
            }
        } catch (InterruptedException e) {
            if (eventLogger.isErrorEnabled()) {
                eventLogger.error(CommonErrorInfo.THRAD_ERR_INTERRUPTED, e);
            }
        }
    }

    /**
     * 处理具体的事件
     *
     * @param event
     */
    @SuppressWarnings("unchecked")
    public void process(IEvent event) {
        if (event == null) {
            if (eventLogger.isWarnEnabled()) {
                eventLogger.warn("[#CORE.QueueMessageExecutorProcessor.process] ["
                        + CommonErrorInfo.EVENT_PRO_NULL_MSG + "]");
            }
            return;
        }
        long begin = 0;
        if (eventLogger.isInfoEnabled()) {
            begin = System.nanoTime();
        }
        this.statisticsMessageCount++;
        try {
//            long sessionId = msg.getSessionId();
//            ClientSessionUpdater onlineSessionService = LocalMananger.getInstance().getClientSessionUpdater();
//            MinaGameClientSession clientSesion = onlineSessionService.getClientSession(sessionId);
//            if(clientSesion != null){
//                logger.debug("processor session" + clientSesion.getPlayerId() + " process message" + msg.getCommandId());
//                clientSesion.addGameMessage(msg);
//
//            }else{
//                logger.debug("session is closed, the message is unDispatch");
//            }
        } catch (Exception e) {
            if (eventLogger.isErrorEnabled()) {
//                eventLogger.error(ErrorsUtil.error("Error",
//                        "#.QueueMessageExecutorProcessor.process", "param"), e);
            }

        } finally {
            if (eventLogger.isInfoEnabled()) {
                // 特例，统计时间跨度
                long time = (System.nanoTime() - begin) / (1000 * 1000);
                if (time > 1) {
//                    eventLogger.info("#CORE.MSG.PROCESS.DISPATCH_STATICS disptach Message id:"
//                            + msg.getCommandId() + " Time:"
//                            + time + "ms" + " Total:"
//                            + this.statisticsMessageCount);
                }
            }
        }
    }

    private final class Worker implements Runnable {
        @Override
        public void run() {
            while (true) {
                try {
                    process(queue.take());
                    if (eventLogger.isDebugEnabled()) {
                        eventLogger.debug("run queue size:" + queue.size());
                    }
                } catch (InterruptedException e) {
                    if (eventLogger.isWarnEnabled()) {
                        eventLogger
                                .warn("[#CORE.AsyncEventService.run] [Stop process]");
                    }
                    Thread.currentThread().interrupt();
                    break;
                } catch (Exception e) {
                    eventLogger.error(CommonErrorInfo.EVENT_PRO_ERROR, e);
                }
            }
        }
    }
}
