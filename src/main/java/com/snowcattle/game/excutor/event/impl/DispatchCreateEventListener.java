package com.snowcattle.game.excutor.event.impl;

import com.snowcattle.game.excutor.thread.DispatchThread;

/**
 * Created by jiangwenping on 17/1/11.
 */
public class DispatchCreateEventListener extends CreateEventListener {

    private DispatchThread dispatchThread ;

    public DispatchCreateEventListener(DispatchThread dispatchThread) {
        super();
        this.dispatchThread = dispatchThread;

    }
}
