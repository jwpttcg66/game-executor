package com.snowcattle.game.excutor.event.impl.listener;

import com.snowcattle.game.excutor.event.AbstractEventListener;
import com.snowcattle.game.excutor.utils.Constants;

/**
 * Created by jiangwenping on 17/1/16.
 */
public class ReadyFinishEventListener extends AbstractEventListener {
    @Override
    public void initEventType() {
        register(Constants.EventTypeConstans.readyFinishEventType);
    }
}
