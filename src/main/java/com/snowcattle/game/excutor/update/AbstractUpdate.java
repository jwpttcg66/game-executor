package com.snowcattle.game.excutor.update;

/**
 * Created by jwp on 2017/1/19.
 * 基本的抽象
 */
public abstract class AbstractUpdate implements IUpdate {

    //是否存放标志
    private boolean activeFlag = true;
    //标示id
    private long id;

    @Override
    public long getId() {
        return id;
    }

    @Override
    public boolean isActive() {
        return activeFlag;
    }

    @Override
    public void setActive(boolean activeFlag) {
        this.activeFlag = activeFlag;
    }

    public void setId(long id) {
        this.id = id;
    }
}

