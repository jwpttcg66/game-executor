package com.snowcattle.game.executor.update.thread.listener;

import com.snowcattle.future.ITaskFuture;
import com.snowcattle.future.ITaskFutureListener;
import com.snowcattle.game.executor.event.EventParam;
import com.snowcattle.game.executor.event.impl.event.UpdateEvent;
import com.snowcattle.game.executor.update.thread.update.LockSupportUpdateFuture;
import com.snowcattle.game.executor.update.entity.IUpdate;
import com.snowcattle.game.executor.common.utils.Constants;
import com.snowcattle.game.executor.common.utils.Loggers;

import java.util.concurrent.locks.LockSupport;

/**
 * Created by jiangwenping on 17/1/18.
 */
public class LockSupportUpdateFutureListener implements ITaskFutureListener {

    @Override
    public void operationComplete(ITaskFuture iTaskFuture) throws Exception {
        if(Loggers.utilLogger.isDebugEnabled()){
            IUpdate iUpdate = (IUpdate) iTaskFuture.get();
            Loggers.utilLogger.debug("update complete event id " + iUpdate.getId());
        }
        LockSupportUpdateFuture lockSupportUpdateFuture = (LockSupportUpdateFuture) iTaskFuture;
        IUpdate iUpdate = (IUpdate) iTaskFuture.get();
        //事件总线增加更新完成通知
        EventParam<IUpdate> params = new EventParam<IUpdate>(iUpdate);
        UpdateEvent event = new UpdateEvent(Constants.EventTypeConstans.updateEventType, iUpdate.getId(), params);
        event.setUpdateAliveFlag(iUpdate.isActive());
        lockSupportUpdateFuture.getDispatchThread().addUpdateEvent(event);
        //解锁
        LockSupport.unpark(lockSupportUpdateFuture.getDispatchThread());
    }
}
