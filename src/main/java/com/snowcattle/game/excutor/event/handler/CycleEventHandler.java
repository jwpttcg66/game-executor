package com.snowcattle.game.excutor.event.handler;

import com.lmax.disruptor.EventReleaseAware;
import com.lmax.disruptor.EventReleaser;
import com.lmax.disruptor.WorkHandler;
import com.snowcattle.game.excutor.event.CycleEvent;

/**
 * Created by jiangwenping on 17/4/24.
 */
public class CycleEventHandler implements WorkHandler<CycleEvent>, EventReleaseAware{

    private EventReleaser eventReleaser;
    @Override
    public void setEventReleaser(EventReleaser eventReleaser) {
        this.eventReleaser = eventReleaser;
    }

    @Override
    public void onEvent(final CycleEvent cycleEvent) throws Exception {
        eventReleaser.release();
        //todo 线程调配
        System.out.println(cycleEvent);
    }
}
