package com.snowcattle.game.excutor.event.impl;

import com.snowcattle.game.excutor.Constants;
import com.snowcattle.game.excutor.event.EventListener;
import com.snowcattle.game.excutor.thread.DispatchThread;

/**
 * Created by jiangwenping on 17/1/9.
 */
public class CreateEventListener extends EventListener{

    private DispatchThread dispatchThread ;

    public CreateEventListener(DispatchThread dispatchThread) {
        super();
        this.dispatchThread = dispatchThread;

    }

    @Override
    public void initEventType() {
        register(Constants.EventTypeConstans.createEventType);
    }
}
