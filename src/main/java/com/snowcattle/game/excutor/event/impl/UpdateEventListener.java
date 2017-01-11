package com.snowcattle.game.excutor.event.impl;

import com.snowcattle.game.excutor.utils.Constants;
import com.snowcattle.game.excutor.event.EventListener;

/**
 * Created by jiangwenping on 17/1/11.
 * 更新监听器
 */
public class UpdateEventListener extends EventListener{

    @Override
    public void initEventType() {
        register(Constants.EventTypeConstans.updateEventType);
    }
}
