package com.snowcattle.game.excutor.service;

import com.snowcattle.game.excutor.event.CycleEvent;
import com.snowcattle.game.excutor.event.EventBus;
import com.snowcattle.game.excutor.event.EventParam;
import com.snowcattle.game.excutor.event.impl.CreateEvent;
import com.snowcattle.game.excutor.pool.UpdateExecutorService;
import com.snowcattle.game.excutor.thread.DispatchThread;
import com.snowcattle.game.excutor.update.IUpdate;
import com.snowcattle.game.excutor.utils.Constants;
import com.snowcattle.game.excutor.utils.Loggers;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.LockSupport;

/**
 * Created by jiangwenping on 17/1/12.
 * 负责循环更新服务
 *  记录更新器缓存
 *  启动分配线程还有更新服务
 *
 *  分配事件到分配线程
 */
public class UpdateService {

    private DispatchThread dispatchThread;
    //处理创建，销毁的eventBus
    private EventBus eventBus;
    private UpdateExecutorService updateExecutorService;
    //记录当前循环的更新接口
    private Map<Long, IUpdate> updateMap = new ConcurrentHashMap<Long, IUpdate>();

    public UpdateService(DispatchThread dispatchThread, EventBus eventBus, UpdateExecutorService updateExecutorService) {
        this.dispatchThread = dispatchThread;
        this.eventBus = eventBus;
        this.updateExecutorService = updateExecutorService;
    }

    public void addReadyCreateEvent(CycleEvent event){
        EventParam[] eventParams = event.getParams();
        IUpdate  iUpdate = (IUpdate) eventParams[0].getT();
        updateMap.put(event.getId(), iUpdate);
        //通知dispatchThread
        if(Loggers.utilLogger.isDebugEnabled()) {
            Loggers.utilLogger.debug("通知" + iUpdate.getId() + " dispatch");
        }
        CreateEvent createEvent = new CreateEvent(Constants.EventTypeConstans.createEventType, eventParams);
        dispatchThread.getEventBus().addEvent(createEvent);
        LockSupport.unpark(dispatchThread);
    }

    public void addReadyFinishEvent(CycleEvent event){
        updateMap.remove(event.getId());
        //通知dispatchThread
    }

    public DispatchThread getDispatchThread() {
        return dispatchThread;
    }

    public void setDispatchThread(DispatchThread dispatchThread) {
        this.dispatchThread = dispatchThread;
    }

    public EventBus getEventBus() {
        return eventBus;
    }

    public void setEventBus(EventBus eventBus) {
        this.eventBus = eventBus;
    }

    public UpdateExecutorService getUpdateExecutorService() {
        return updateExecutorService;
    }

    public void setUpdateExecutorService(UpdateExecutorService updateExecutorService) {
        this.updateExecutorService = updateExecutorService;
    }

    public void shutDown(){
        updateExecutorService.shutdown();
        dispatchThread.shutDown();
        this.updateMap.clear();
    }
}
