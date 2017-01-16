package com.snowcattle.game.excutor.event.impl;

import com.snowcattle.game.excutor.event.EventListener;
import com.snowcattle.game.excutor.utils.Constants;

/**
 * Created by jiangwenping on 17/1/16.
 */
public class ReadyFinishEventListener extends EventListener {
    @Override
    public void initEventType() {
        register(Constants.EventTypeConstans.readyFinishEventType);
    }
}
