package com.snowcattle.game.excutor.thread.update.bind;

import com.snowcattle.game.excutor.event.EventBus;
import com.snowcattle.game.excutor.event.EventParam;
import com.snowcattle.game.excutor.event.impl.UpdateEvent;
import com.snowcattle.game.excutor.thread.UpdateThread;
import com.snowcattle.game.excutor.thread.dispatch.DispatchThread;
import com.snowcattle.game.excutor.update.IUpdate;
import com.snowcattle.game.excutor.utils.Constants;

/**
 * Created by jiangwenping on 17/1/9.
 * 带预置锁的执行器
 */
public abstract class BindingLockSupportUpdateThread extends UpdateThread {

    private DispatchThread dispatchThread;

    public BindingLockSupportUpdateThread(DispatchThread dispatchThread, EventBus eventBus) {
        super(eventBus);
        this.dispatchThread = dispatchThread;
    }

    public void run() {
        if(getiUpdate() != null) {
            IUpdate excutorUpdate = getiUpdate();
            excutorUpdate.update();
            setiUpdate(null);

            //事件总线增加更新完成通知
            EventParam<IUpdate> params = new EventParam<IUpdate>(excutorUpdate);
            UpdateEvent event = new UpdateEvent(Constants.EventTypeConstans.updateEventType, params);
            event.setUpdateAliveFlag(getiUpdate().isActive());
            getEventBus().addEvent(event);
            getDispatchThread().unpark();
        }
    }


    public DispatchThread getDispatchThread() {
        return dispatchThread;
    }
}
