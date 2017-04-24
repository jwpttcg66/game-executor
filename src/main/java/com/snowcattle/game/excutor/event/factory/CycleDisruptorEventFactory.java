package com.snowcattle.game.excutor.event.factory;

import com.lmax.disruptor.EventFactory;
import com.snowcattle.game.excutor.event.CycleEvent;

/**
 * Created by jiangwenping on 17/4/24.
 */
public class CycleDisruptorEventFactory implements EventFactory<CycleEvent> {

    @Override
    public CycleEvent newInstance() {
        return new CycleEvent();
    }

}
