package com.snowcattle.game.excutor.thread;

import com.snowcattle.game.excutor.event.EventBus;
import com.snowcattle.game.excutor.event.EventParam;
import com.snowcattle.game.excutor.event.impl.UpdateEvent;
import com.snowcattle.game.excutor.update.IUpdate;
import com.snowcattle.game.excutor.utils.Constants;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.locks.LockSupport;

/**
 * Created by jwp on 2017/2/23.
 */
public class SingleLockSupportUpdateThread extends LockSupportUpdateThread{

    private Queue<IUpdate> iUpdates;
    private Queue<IUpdate> fetchUpdates;
    private boolean runningFlag;
    public SingleLockSupportUpdateThread(DispatchThread dispatchThread, EventBus eventBus) {
        super(dispatchThread, eventBus);
        iUpdates = new ConcurrentLinkedQueue<IUpdate>();
        fetchUpdates = new ConcurrentLinkedQueue<IUpdate>();
        runningFlag = true;
    }

    public void run() {

        do {
            for (;;) {
                fetchUpdates();
                IUpdate excutorUpdate = fetchUpdates.poll();
                if (excutorUpdate!= null) {
                    excutorUpdate.update();
                    setiUpdate(null);

                    //事件总线增加更新完成通知
                    EventParam<IUpdate> params = new EventParam<IUpdate>(excutorUpdate);
                    UpdateEvent event = new UpdateEvent(Constants.EventTypeConstans.updateEventType, params);
                    getEventBus().addEvent(event);
                    LockSupport.unpark(getDispatchThread());
                }

            }

            //执行结束，进入等待状态，等待唤醒

        }while (runningFlag);

    }

    public void fetchUpdates(){
        fetchUpdates.addAll(iUpdates);
    }


}
