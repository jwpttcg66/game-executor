package com.snowcattle.game.excutor.thread;

import com.snowcattle.game.excutor.event.EventParam;
import com.snowcattle.game.excutor.event.impl.UpdateEvent;
import com.snowcattle.game.excutor.update.IUpdate;
import com.snowcattle.game.excutor.utils.Constants;

import java.util.ArrayList;
import java.util.Iterator;
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

    private int fetchSize;

    public SingleLockSupportUpdateThread(DispatchThread dispatchThread, Queue<IUpdate> iUpdates, ArrayBlockingQueue<IUpdate> fetchUpdates) {
        super(dispatchThread, dispatchThread.getEventBus());
        this.iUpdates = iUpdates;
        this.fetchUpdates = fetchUpdates;
    }

    @Override
    public void run() {

        for (; ; ) {
            fetchUpdates();
            for (; ; ) {
                IUpdate excutorUpdate = null;
                try {
                    excutorUpdate = fetchUpdates.take();
                    if (excutorUpdate != null) {
                        excutorUpdate.update();
                        sendFinish(excutorUpdate);
                    } 
                } catch (Exception e) {
                    break;
                }

            }
            cleanFetch();

        }

    }

    public void cleanFetch(){
        fetchSize = 0;
    }

    public void fetchUpdates() {
        Iterator<IUpdate> iUpdateIterator = iUpdates.iterator();
        while (iUpdateIterator.hasNext()){
            fetchUpdates.add(iUpdateIterator.next());
            fetchSize++;
        }
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
