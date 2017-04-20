package com.snowcattle.game.excutor.update.async;

import com.snowcattle.game.excutor.event.EventParam;
import com.snowcattle.game.excutor.event.EventType;
import com.snowcattle.game.excutor.event.impl.event.UpdateEvent;

/**
 * Created by jiangwenping on 17/1/12.
 */
public class TestUpdateEvent extends UpdateEvent {

    public TestUpdateEvent(EventType eventType, EventParam... parms) {
        super(eventType, parms);
    }

    public void call() {
        EventParam[] eventParams = getParams();
        int a = (int) eventParams[0].getT();
        System.out.println(eventParams[0].getT() + "float" + eventParams[1].getT());
    }
}
