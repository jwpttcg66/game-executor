package com.snowcattle.game.excutor.event.impl;

import com.snowcattle.game.excutor.Constants;
import com.snowcattle.game.excutor.event.EventListener;
import com.snowcattle.game.excutor.thread.DispatchThread;

/**
 * Created by jiangwenping on 17/1/11.
 * 更新监听器
 */
public class UpdateEventListener extends EventListener{

    private DispatchThread dispatchThread ;

    public UpdateEventListener(DispatchThread dispatchThread) {
        super();
        this.dispatchThread = dispatchThread;

    }
    @Override
    public void initEventType() {
        register(Constants.EventTypeConstans.updateEventType);
    }
}
