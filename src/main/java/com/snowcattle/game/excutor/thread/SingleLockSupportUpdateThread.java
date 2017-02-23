package com.snowcattle.game.excutor.thread;

import com.snowcattle.game.excutor.event.EventBus;
import com.snowcattle.game.excutor.event.EventParam;
import com.snowcattle.game.excutor.event.impl.UpdateEvent;
import com.snowcattle.game.excutor.update.IUpdate;
import com.snowcattle.game.excutor.utils.Constants;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.locks.LockSupport;

/**
 * Created by jwp on 2017/2/23.
 */
public class SingleLockSupportUpdateThread extends LockSupportUpdateThread {

    private Queue<IUpdate> iUpdates;
    private Queue<IUpdate> fetchUpdates;
    private List<IUpdate> updateFinishList;
    private boolean runningFlag;

    public SingleLockSupportUpdateThread(DispatchThread dispatchThread, EventBus eventBus) {
        super(dispatchThread, eventBus);
        iUpdates = new ConcurrentLinkedQueue<IUpdate>();
        fetchUpdates = new ConcurrentLinkedQueue<IUpdate>();
        updateFinishList = new ArrayList<>(updateFinishList);
        runningFlag = true;
    }

    @Override
    public void run() {

        do {
            fetchUpdates();
            for (; ; ) {
                IUpdate excutorUpdate = fetchUpdates.poll();
                if (excutorUpdate != null) {
                    excutorUpdate.update();
                    updateFinishList.add(excutorUpdate);
                } else {
                    break;
                }

            }
            //执行结束，进入等待状态，等待唤醒
            try {
                sendFinish();
                wait();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } while (runningFlag);

    }

    public void fetchUpdates() {
        fetchUpdates.addAll(iUpdates);
    }

    public void sendFinish() {
        for (IUpdate excutorUpdate : updateFinishList) {
            //事件总线增加更新完成通知
            EventParam<IUpdate> params = new EventParam<IUpdate>(excutorUpdate);
            UpdateEvent event = new UpdateEvent(Constants.EventTypeConstans.updateEventType, params);
            getEventBus().addEvent(event);
        }
        updateFinishList.clear();
        LockSupport.unpark(getDispatchThread());
    }


}
