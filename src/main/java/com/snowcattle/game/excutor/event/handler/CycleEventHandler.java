package com.snowcattle.game.excutor.event.handler;

import com.lmax.disruptor.EventReleaseAware;
import com.lmax.disruptor.EventReleaser;
import com.lmax.disruptor.WorkHandler;
import com.snowcattle.game.excutor.event.CycleEvent;
import com.snowcattle.game.excutor.event.EventBus;

/**
 * Created by jiangwenping on 17/4/24.
 */
public class CycleEventHandler implements WorkHandler<CycleEvent>, EventReleaseAware{

    private EventReleaser eventReleaser;

    private EventBus eventBus;

    @Override
    public void setEventReleaser(EventReleaser eventReleaser) {
        this.eventReleaser = eventReleaser;
    }

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
    public void onEvent(final CycleEvent cycleEvent) throws Exception {
        eventReleaser.release();
        eventBus.handleSingleEvent(cycleEvent);
//        if(cycleEvent.getEventType().getIndex() == EventTypeEnum.CREATE.ordinal()){
//
//        }else if(cycleEvent.getEventType().getIndex() == EventTypeEnum.UPDATE.ordinal()){
//
//        }else if(cycleEvent.getEventType().getIndex() == EventTypeEnum.FINISH.ordinal()){
//
//        }
    }
}
