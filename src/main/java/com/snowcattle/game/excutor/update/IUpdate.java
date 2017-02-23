package com.snowcattle.game.excutor.update;

/**
 * Created by jiangwenping on 17/1/9.
 * 基础循环接口
 */
public interface IUpdate {
    public void update();
    public long getId();
    public boolean isActive();
    public void setActive(boolean activeFlag);
    public void setId(long id);
}
