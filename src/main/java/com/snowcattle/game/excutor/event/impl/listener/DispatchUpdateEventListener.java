package com.snowcattle.game.excutor.event.impl.listener;

import com.snowcattle.game.excutor.event.EventParam;
import com.snowcattle.game.excutor.event.common.IEvent;
import com.snowcattle.game.excutor.event.impl.event.FinishEvent;
import com.snowcattle.game.excutor.event.impl.event.UpdateEvent;
import com.snowcattle.game.excutor.pool.IUpdateExcutor;
import com.snowcattle.game.excutor.service.UpdateService;
import com.snowcattle.game.excutor.thread.dispatch.LockSupportDisptachThread;
import com.snowcattle.game.excutor.entity.IUpdate;
import com.snowcattle.game.excutor.utils.Constants;

/**
 * Created by jiangwenping on 17/1/11.
 */
public class DispatchUpdateEventListener extends UpdateEventListener {
    private LockSupportDisptachThread dispatchThread;
    private UpdateService updateService;
    public DispatchUpdateEventListener(LockSupportDisptachThread dispatchThread, UpdateService updateService) {
        this.dispatchThread = dispatchThread;
        this.updateService = updateService;
    }


    public void fireEvent(IEvent event) {
//        if(Loggers.utilLogger.isDebugEnabled()){
//            Loggers.utilLogger.debug("处理update");
//        }
        super.fireEvent(event);

        //提交执行线程
        UpdateEvent updateEvent = (UpdateEvent) event;
        EventParam[] eventParams = event.getParams();
        for(EventParam eventParam: eventParams) {
            IUpdate iUpdate = (IUpdate) eventParam.getT();
            boolean aliveFlag = updateEvent.isUpdateAliveFlag();
            if (aliveFlag) {
                IUpdateExcutor iUpdateExcutor = dispatchThread.getiUpdateExcutor();
                iUpdateExcutor.excutorUpdate(dispatchThread, iUpdate, updateEvent.isInitFlag(), updateEvent.getUpdateExcutorIndex());
            } else {
                FinishEvent finishEvent = new FinishEvent(Constants.EventTypeConstans.finishEventType, iUpdate.getId(), eventParams);
                dispatchThread.addFinishEvent(finishEvent);
            }
        }

    }
}
