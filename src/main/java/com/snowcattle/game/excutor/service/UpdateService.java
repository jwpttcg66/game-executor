package com.snowcattle.game.excutor.service;

import com.snowcattle.game.excutor.thread.DispatchThread;

/**
 * Created by jiangwenping on 17/1/12.
 * 负责循环更新服务
 *  启动分配线程还有更新服务
 *
 *  分配事件到分配线程
 */
public class UpdateService {

    private DispatchThread dispatchThread;
    private UpdateService updateService;

    public UpdateService(DispatchThread dispatchThread, UpdateService updateService) {
        this.dispatchThread = dispatchThread;
        this.updateService = updateService;
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



}
