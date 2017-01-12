package com.snowcattle.game.excutor.thread;

import com.snowcattle.game.excutor.event.Event;
import com.snowcattle.game.excutor.event.EventBus;

import java.util.concurrent.locks.LockSupport;

/**
 * Created by jiangwenping on 17/1/9.
 * 带预置锁的分配器
 */
public class LockSupportDisptachThread extends DispatchThread{

    public LockSupportDisptachThread(EventBus eventBus) {
        super(eventBus);
    }
    public void run() {
        int maxSize = 10000;
        while (true) {
            getEventBus().cycle(maxSize);
            LockSupport.park();
        }
    }

    public void addEvent(Event event){
        getEventBus().addEvent(event);
        LockSupport.unpark(this);
    }
}
