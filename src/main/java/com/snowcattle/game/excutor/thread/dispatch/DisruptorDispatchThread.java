package com.snowcattle.game.excutor.thread.dispatch;

import com.snowcattle.game.excutor.event.EventBus;

/**
 * Created by jiangwenping on 17/4/24.
 *
 */
public class DisruptorDispatchThread extends DispatchThread{

    public DisruptorDispatchThread(EventBus eventBus) {
        super(eventBus);
    }


}
