package com.snowcattle.game.excutor.event.impl;

import com.snowcattle.game.excutor.utils.Constants;
import com.snowcattle.game.excutor.event.EventListener;

/**
 * Created by jiangwenping on 17/1/9.
 * 创建监听器
 */
public class CreateEventListener extends EventListener{

    @Override
    public void initEventType() {
        register(Constants.EventTypeConstans.createEventType);
    }
}
