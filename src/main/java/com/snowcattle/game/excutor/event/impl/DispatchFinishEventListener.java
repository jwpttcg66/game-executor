package com.snowcattle.game.excutor.event.impl;

import com.snowcattle.game.excutor.thread.DispatchThread;

/**
 * Created by jiangwenping on 17/1/11.
 */
public class DispatchFinishEventListener extends FinishEventListener {

    public DispatchFinishEventListener(DispatchThread dispatchThread) {
        this.dispatchThread = dispatchThread;
    }

    private DispatchThread dispatchThread;
}
