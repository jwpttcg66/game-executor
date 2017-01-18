package com.snowcattle.game.excutor.thread.listener;

import com.snowcattle.future.ITaskFuture;
import com.snowcattle.future.ITaskFutureListener;
import com.snowcattle.game.excutor.event.EventParam;
import com.snowcattle.game.excutor.event.impl.UpdateEvent;
import com.snowcattle.game.excutor.thread.LockSupportUpdateFuture;
import com.snowcattle.game.excutor.update.IUpdate;
import com.snowcattle.game.excutor.utils.Constants;

import java.util.concurrent.locks.LockSupport;

/**
 * Created by jiangwenping on 17/1/18.
 */
public class LockSupportUpdateFutureListener implements ITaskFutureListener {

    @Override
    public void operationComplete(ITaskFuture iTaskFuture) throws Exception {
        LockSupportUpdateFuture lockSupportUpdateFuture = (LockSupportUpdateFuture) iTaskFuture;

        IUpdate iUpdate = (IUpdate) iTaskFuture.get();
        //事件总线增加更新完成通知
        EventParam<IUpdate> params = new EventParam<IUpdate>(iUpdate);
        UpdateEvent event = new UpdateEvent(Constants.EventTypeConstans.updateEventType, params);
        lockSupportUpdateFuture.getDispatchThread().addUpdateEvent(event);

        LockSupport.unpark(lockSupportUpdateFuture.getDispatchThread());
    }
}
