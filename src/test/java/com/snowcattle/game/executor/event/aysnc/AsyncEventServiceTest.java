package com.snowcattle.game.executor.event.aysnc;

import com.snowcattle.game.executor.event.EventBus;
import com.snowcattle.game.executor.event.service.AsyncEventService;

/**
 * Created by jwp on 2017/5/5.
 */
public class AsyncEventServiceTest {
    public static void main(String[] args) throws Exception {
        EventBus eventBus = new EventBus();
        AsyncEventService asyncEventService = new AsyncEventService(eventBus, Short.MAX_VALUE, 20, "worker", Short.MAX_VALUE);
        asyncEventService.startUp();
    }
}
