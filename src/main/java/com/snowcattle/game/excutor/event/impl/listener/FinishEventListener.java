package com.snowcattle.game.excutor.event.impl.listener;

import com.snowcattle.game.excutor.utils.Constants;
import com.snowcattle.game.excutor.event.AbstractEventListener;

/**
 * Created by jiangwenping on 17/1/11.
 * 完成监听器
 */
public class FinishEventListener extends AbstractEventListener {

    @Override
    public void initEventType() {
        register(Constants.EventTypeConstans.finishEventType);
    }

}
