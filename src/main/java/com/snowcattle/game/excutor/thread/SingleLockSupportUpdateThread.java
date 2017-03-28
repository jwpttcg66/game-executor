package com.snowcattle.game.excutor.thread;

import com.snowcattle.game.excutor.event.EventParam;
import com.snowcattle.game.excutor.event.impl.UpdateEvent;
import com.snowcattle.game.excutor.pool.excutor.SingleThreadEventExecutor;
import com.snowcattle.game.excutor.update.IUpdate;
import com.snowcattle.game.excutor.utils.Constants;
import com.snowcattle.game.excutor.utils.Loggers;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.locks.LockSupport;
import java.util.logging.Logger;

/**
 * Created by jwp on 2017/2/23.
 * 线程一旦启动不会停止,使用arrayblockqueue进行阻塞fetchUpdates，
 * 并且通过加入一个null update来进行wakeup
 */
public class SingleLockSupportUpdateThread extends LockSupportUpdateThread {

    private Queue<IUpdate> iUpdates;
    //这里会用来阻塞
    private BlockingQueue<IUpdate> fetchUpdates;

    private int fetchSize;
    private int updateSize;

    private List<IUpdate> finishList;

    private SingleThreadEventExecutor singleThreadEventExecutor;
    public SingleLockSupportUpdateThread(SingleThreadEventExecutor singleThreadEventExecutor, DispatchThread dispatchThread, Queue<IUpdate> iUpdates, BlockingQueue<IUpdate> fetchUpdates) {
        super(dispatchThread, dispatchThread.getEventBus());
        this.singleThreadEventExecutor = singleThreadEventExecutor;
        this.iUpdates = iUpdates;
        this.fetchUpdates = fetchUpdates;
        this.finishList = new ArrayList<IUpdate>();
    }

    @Override
    public void run() {

        for (; ; ) {
            fetchUpdates();
            for (; ; ) {

                try {
                    IUpdate excutorUpdate = fetchUpdates.take();
                    if (excutorUpdate != null) {
                        if(excutorUpdate == SingleThreadEventExecutor.nullWeakUpUpdate){
                            continue;
                        }
                        excutorUpdate.update();
                        updateSize++;
                        finishList.add(excutorUpdate);
                    }else{
                        break;
                    }
                } catch (Exception e) {
                    Loggers.errorLogger.error(e.toString(), e);
                    break;
                }

                if(updateSize == fetchSize){
                    sendFinishList();
                    break;
                }

            }
            cleanFetch();

            //这里会运行的太快，需要阻塞
            try {
                fetchUpdates.take();
            } catch (InterruptedException e) {
                Loggers.errorLogger.error(e.toString(), e);
            }
        }

    }

    public void cleanFetch(){
        fetchSize = 0;
        updateSize = 0;
    }

    public void fetchUpdates() {
        Iterator<IUpdate> iUpdateIterator = iUpdates.iterator();
        while (iUpdateIterator.hasNext()){
            fetchUpdates.add(iUpdateIterator.next());
            fetchSize++;
        }
    }

    public void sendFinish(IUpdate excutorUpdate) {
        //如果生命周期结束了，直接进行销毁
        if(!excutorUpdate.isActive()){
            singleThreadEventExecutor.removeTaskQueue(excutorUpdate);
        }
        //事件总线增加更新完成通知
        EventParam<IUpdate> params = new EventParam<IUpdate>(excutorUpdate);
        UpdateEvent event = new UpdateEvent(Constants.EventTypeConstans.updateEventType, params);
        event.setUpdateExcutorIndex(singleThreadEventExecutor.getUpdateExcutorIndex());
        event.setUpdateAliveFlag(excutorUpdate.isActive());
        getEventBus().addEvent(event);

    }

    public void sendFinishList(){

        //事件总线增加更新完成通知
        for(IUpdate excutorUpdate : finishList){
//            if(Loggers.utilLogger.isDebugEnabled()) {
//                Loggers.utilLogger.debug(excutorUpdate.getId() + "发送存活" + excutorUpdate.isActive());
//            }
            sendFinish(excutorUpdate);
        }
        finishList.clear();
        getDispatchThread().unpark();

    }

    public void addUpdate(IUpdate iUpdate){
        iUpdates.add(iUpdate);
    }


}
