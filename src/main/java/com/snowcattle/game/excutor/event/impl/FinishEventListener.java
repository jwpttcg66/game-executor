package com.snowcattle.game.excutor.event.impl;

import com.snowcattle.game.excutor.Constants;
import com.snowcattle.game.excutor.event.EventListener;

/**
 * Created by jiangwenping on 17/1/11.
 * 完成监听器
 */
public class FinishEventListener extends EventListener {

    @Override
    public void initEventType() {
        register(Constants.EventTypeConstans.finishEventType);
    }

}
