package com.snowcattle.game.excutor.thread;

import com.snowcattle.game.excutor.event.EventParam;
import com.snowcattle.game.excutor.event.impl.UpdateEvent;
import com.snowcattle.game.excutor.update.IUpdate;
import com.snowcattle.game.excutor.utils.Constants;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.locks.LockSupport;

/**
 * Created by jwp on 2017/2/23.
 * 线程一旦启动不会停止,使用arrayblockqueue进行阻塞fetchUpdates，
 * 并且通过加入一个null update来进行wakeup
 */
public class SingleLockSupportUpdateThread extends LockSupportUpdateThread {

    private Queue<IUpdate> iUpdates;
    //这里会用来阻塞
    private ArrayBlockingQueue<IUpdate> fetchUpdates;
    private boolean runningFlag;

    public SingleLockSupportUpdateThread(DispatchThread dispatchThread) {
        super(dispatchThread, dispatchThread.getEventBus());
        iUpdates = new ConcurrentLinkedQueue<IUpdate>();
        fetchUpdates = new ArrayBlockingQueue<IUpdate>(Short.MAX_VALUE);
        runningFlag = true;
    }

    @Override
    public void run() {

        do {
            for (; ; ) {
                fetchUpdates();
                for (; ; ) {
                    IUpdate excutorUpdate = null;
                    try {
                        excutorUpdate = fetchUpdates.take();
                        if (excutorUpdate != null) {
                            excutorUpdate.update();
                            sendFinish(excutorUpdate);
                        } else {
                            break;
                        }
                    } catch (Exception e) {
                        break;
                    }

                }

            }
        } while (runningFlag);

    }

    public void fetchUpdates() {
        fetchUpdates.addAll(iUpdates);
    }

    public void sendFinish(IUpdate excutorUpdate) {
        //事件总线增加更新完成通知
        EventParam<IUpdate> params = new EventParam<IUpdate>(excutorUpdate);
        UpdateEvent event = new UpdateEvent(Constants.EventTypeConstans.updateEventType, params);
        getEventBus().addEvent(event);
        LockSupport.unpark(getDispatchThread());
    }

    public void addUpdate(IUpdate iUpdate){
        iUpdates.add(iUpdate);
    }


}
