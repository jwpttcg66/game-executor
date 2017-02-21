package com.snowcattle.game.excutor.event.impl;

import com.snowcattle.game.excutor.event.EventParam;
import com.snowcattle.game.excutor.event.IEvent;
import com.snowcattle.game.excutor.service.UpdateService;
import com.snowcattle.game.excutor.thread.DispatchThread;
import com.snowcattle.game.excutor.update.IUpdate;
import com.snowcattle.game.excutor.utils.Constants;

/**
 * Created by jiangwenping on 17/1/11.
 */
public class DispatchFinishEventListener extends FinishEventListener {

    private UpdateService updateService;
    private DispatchThread dispatchThread;

    public DispatchFinishEventListener(DispatchThread dispatchThread, UpdateService updateService) {
        this.dispatchThread = dispatchThread;
        this.updateService = updateService;
    }

    public void fireEvent(IEvent event) {
        super.fireEvent(event);
        //提交更新服务器 执行完成调度
        EventParam[] eventParams = event.getParams();
        IUpdate iUpdate = (IUpdate) eventParams[0].getT();
        FinishedEvent finishedEvent = new FinishedEvent(Constants.EventTypeConstans.readyFinishEventType, iUpdate.getId(), event.getParams());
        this.updateService.notifyFinishedEvent(finishedEvent);
    }

}
