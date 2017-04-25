package com.snowcattle.game.excutor.event.handler;

import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.EventReleaser;
import com.snowcattle.game.excutor.event.CycleEvent;
import com.snowcattle.game.excutor.event.EventBus;

/**
 * Created by jiangwenping on 17/4/24.
 */
public class CycleEventHandler implements EventHandler<CycleEvent>{

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
    public void onEvent(final CycleEvent cycleEvent, final long sequence, boolean endOfbatch) throws Exception {
        System.out.println("dd");
//        eventReleaser.release();
//        eventBus.handleSingleEvent(cycleEvent);
    }
}
