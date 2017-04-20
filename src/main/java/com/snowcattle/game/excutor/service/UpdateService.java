package com.snowcattle.game.excutor.service;

import com.snowcattle.game.excutor.event.CycleEvent;
import com.snowcattle.game.excutor.event.EventParam;
import com.snowcattle.game.excutor.event.impl.CreateEvent;
import com.snowcattle.game.excutor.event.impl.FinishEvent;
import com.snowcattle.game.excutor.event.impl.FinishedEvent;
import com.snowcattle.game.excutor.event.impl.ReadFinishEvent;
import com.snowcattle.game.excutor.pool.IUpdateExcutor;
import com.snowcattle.game.excutor.thread.dispatch.DispatchThread;
import com.snowcattle.game.excutor.update.IUpdate;
import com.snowcattle.game.excutor.utils.Constants;
import com.snowcattle.game.excutor.utils.Loggers;

import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by jiangwenping on 17/1/12.
 * 负责循环更新服务
 *  记录更新器缓存
 *  启动分配线程还有更新服务
 *
 *  分配事件到分配线程
 */
public class UpdateService <ID extends Serializable> {

    private DispatchThread dispatchThread;
//    //处理创建，销毁的eventBus
//    private EventBus eventBus;
    private IUpdateExcutor iUpdateExcutor;
    //记录当前循环的更新接口
    private Map<ID, IUpdate> updateMap = new ConcurrentHashMap<ID, IUpdate>();

    public UpdateService(DispatchThread dispatchThread, IUpdateExcutor iUpdateExcutor) {
        this.dispatchThread = dispatchThread;
        this.iUpdateExcutor = iUpdateExcutor;
    }

    public void addReadyCreateEvent(CycleEvent event){
        EventParam[] eventParams = event.getParams();
        IUpdate  iUpdate = (IUpdate) eventParams[0].getT();
        updateMap.put((ID) event.getId(), iUpdate);
        //通知dispatchThread
        if(Loggers.utilLogger.isDebugEnabled()) {
            Loggers.utilLogger.debug("readycreate " + iUpdate.getId() + " dispatch");
        }
        CreateEvent createEvent = new CreateEvent(Constants.EventTypeConstans.createEventType, eventParams);
        dispatchThread.addCreateEvent(createEvent);
        dispatchThread.unpark();
    }

    public void addReadyFinishEvent(CycleEvent event){
        ReadFinishEvent readFinishEvent = (ReadFinishEvent) event;
        EventParam[] eventParams = event.getParams();
        //通知dispatchThread
        FinishEvent finishEvent = new FinishEvent(Constants.EventTypeConstans.finishEventType, eventParams);
        dispatchThread.addFinishEvent(finishEvent);
    }

    public void notifyFinishedEvent(CycleEvent event){
        FinishedEvent readFinishEvent = (FinishedEvent) event;
        EventParam[] eventParams = event.getParams();
        //只有distpatch转发结束后，才会才缓存池里销毁
        updateMap.remove(event.getId());
    }

    public DispatchThread getDispatchThread() {
        return dispatchThread;
    }

    public void setDispatchThread(DispatchThread dispatchThread) {
        this.dispatchThread = dispatchThread;
    }

//    public EventBus getEventBus() {
//        return eventBus;
//    }
//
//    public void setEventBus(EventBus eventBus) {
//        this.eventBus = eventBus;
//    }

    public void stop(){
        iUpdateExcutor.stop();
        dispatchThread.shutDown();
        this.updateMap.clear();
    }

    public void start(){
        iUpdateExcutor.start();
        dispatchThread.start();
        this.updateMap.clear();
    }

    public void notifyStart(){
        iUpdateExcutor.start();
        this.updateMap.clear();
    }

    public UpdateService(IUpdateExcutor iUpdateExcutor) {
        this.iUpdateExcutor = iUpdateExcutor;
    }

    public void notifyRun(){
        dispatchThread.notifyRun();
    }
}
