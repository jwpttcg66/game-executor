package com.snowcattle.game.excutor.entity;

import java.io.Serializable;

/**
 * Created by jiangwenping on 17/1/9.
 * 基础循环接口
 */
public interface IUpdate <ID extends Serializable> extends Serializable {
    public void update();
    public ID getId();
    public boolean isActive();
    public void setActive(boolean activeFlag);
}
