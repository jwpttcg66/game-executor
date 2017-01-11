package com.snowcattle.game.excutor.event.impl;

import com.snowcattle.game.excutor.thread.DispatchThread;

/**
 * Created by jiangwenping on 17/1/11.
 */
public class DispatchUpdateEventListener extends UpdateEventListener {
        private DispatchThread dispatchThread ;

    public DispatchUpdateEventListener(DispatchThread dispatchThread) {
        super();
        this.dispatchThread = dispatchThread;
    }
}
