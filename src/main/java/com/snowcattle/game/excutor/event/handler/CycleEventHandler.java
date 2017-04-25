package com.snowcattle.game.excutor.event.handler;

import com.lmax.disruptor.EventReleaser;
import com.lmax.disruptor.WorkHandler;
import com.snowcattle.game.excutor.event.CycleEvent;
import com.snowcattle.game.excutor.event.EventBus;

/**
 * Created by jiangwenping on 17/4/24.
 */
public class CycleEventHandler implements WorkHandler<CycleEvent>{

    private EventReleaser eventReleaser;

    private EventBus eventBus;

    public CycleEventHandler(EventBus eventBus) {
        this.eventBus = eventBus;
    }

    public EventBus getEventBus() {
        return eventBus;
    }

    public void setEventBus(EventBus eventBus) {
        this.eventBus = eventBus;
    }

    @Override
    public void onEvent(CycleEvent cycleEvent) throws Exception {
        eventBus.handleSingleEvent(cycleEvent);
    }
}
