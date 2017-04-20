package com.snowcattle.game.excutor.event.syns;

import com.snowcattle.game.excutor.event.impl.EventBus;
import com.snowcattle.game.excutor.event.EventParam;
import com.snowcattle.game.excutor.event.impl.CreateEventListener;
import com.snowcattle.game.excutor.event.impl.FinishEvent;
import com.snowcattle.game.excutor.event.impl.FinishEventListener;
import com.snowcattle.game.excutor.event.impl.UpdateEventListener;
import com.snowcattle.game.excutor.thread.dispatch.DispatchThread;
import com.snowcattle.game.excutor.utils.Constants;

/**
 * Created by jiangwenping on 17/1/9.
 */
public class SynsEventBusTest {
    public static void main(String[] args) {
        testSynsEvent();
    }

    public static void testSynsEvent(){
        EventBus eventBus = new EventBus();
        DispatchThread dispatchThread = new DispatchThread(eventBus);
        eventBus.addEventListener(new CreateEventListener());
        eventBus.addEventListener(new UpdateEventListener());
        eventBus.addEventListener(new FinishEventListener());
        int maxSize = 1000;
        for(int i = 0; i < 10000; i++) {
            EventParam<Integer> intParam = new EventParam<Integer>(1);
            EventParam<Float> floatEventParam = new EventParam<Float>(2.0f);
//            CreateEvent event = new CreateEvent(Constants.EventTypeConstans.createEventType, intParam, floatEventParam);
//            UpdateEvent event = new UpdateEvent(Constants.EventTypeConstans.updateEventType, intParam, floatEventParam);
            FinishEvent event = new FinishEvent(Constants.EventTypeConstans.finishEventType, intParam, floatEventParam);
            eventBus.addEvent(event);
        }

        eventBus.handleEvent();
    }
}
