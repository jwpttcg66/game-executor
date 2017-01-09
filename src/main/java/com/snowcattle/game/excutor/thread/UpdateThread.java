package com.snowcattle.game.excutor.thread;

import com.snowcattle.game.excutor.event.EventBus;
import com.snowcattle.game.excutor.update.IUpdate;

import java.util.concurrent.locks.LockSupport;

/**
 * Created by jiangwenping on 17/1/9.
 */
public class UpdateThread implements Runnable{

    private DispatchThread dispatchThread;
    private EventBus eventBus;
    private IUpdate iUpdate;

    public void run() {
        iUpdate.update();
        LockSupport.unpark(dispatchThread);
    }
}
