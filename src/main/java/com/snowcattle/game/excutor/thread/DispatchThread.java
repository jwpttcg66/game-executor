package com.snowcattle.game.excutor.thread;

import com.snowcattle.game.excutor.event.EventBus;

/**
 * Created by jiangwenping on 17/1/9.
 */
public class DispatchThread extends Thread{

    private EventBus eventBus;

    public DispatchThread(EventBus eventBus) {
        this.eventBus = eventBus;
    }

    public void run() {
        eventBus.handleEvent();
    }
}
