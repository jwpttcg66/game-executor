package com.snowcattle.game.excutor.service;

import com.snowcattle.game.excutor.event.Event;
import com.snowcattle.game.excutor.thread.DispatchThread;
import com.snowcattle.game.excutor.update.IUpdate;

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
public class UpdateService {

    private DispatchThread dispatchThread;
    private UpdateService updateService;

    //记录当前循环的更新接口
    private Map<Long, IUpdate> updateMap;

    public UpdateService(DispatchThread dispatchThread, UpdateService updateService) {
        this.dispatchThread = dispatchThread;
        this.updateService = updateService;
        this.updateMap = new ConcurrentHashMap<Long, IUpdate>();
    }

    public DispatchThread getDispatchThread() {
        return dispatchThread;
    }

    public void setDispatchThread(DispatchThread dispatchThread) {
        this.dispatchThread = dispatchThread;
    }

    public UpdateService getUpdateService() {
        return updateService;
    }

    public void setUpdateService(UpdateService updateService) {
        this.updateService = updateService;
    }

    public void addCreateEvent(Event event){
        //注册future-listener

    }

    public void addFinishEvent(Event event){

    }
}
