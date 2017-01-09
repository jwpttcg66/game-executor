package com.snowcattle.game.excutor.event;

import com.snowcattle.game.excutor.thread.DispatchThread;

/**
 * Created by jiangwenping on 17/1/9.
 */
public class EventBusTest {
    public static void main(String[] args) {
        EventBus eventBus = new EventBus();
        DispatchThread dispatchThread = new DispatchThread(eventBus);

    }
}
