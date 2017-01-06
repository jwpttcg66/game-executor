package com.snowcattle.game.excutor.event;

/**
 * Created by jiangwenping on 17/1/6.
 * ⌚
 */
public interface IEventDispatch {
    public void addEventListener();
    public void removeEventListener();
    public void addEvent();
    public void handleEvent();
    public void fireEvent();
}
