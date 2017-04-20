package com.snowcattle.game.excutor.entity;

import java.io.Serializable;

/**
 * Created by jwp on 2017/1/19.
 * 基本的抽象
 */
public abstract class AbstractUpdate<ID extends Serializable> implements IUpdate<ID> {

    //是否存放标志
    private boolean activeFlag = true;
    //标示id
    private ID id;

    @Override
    public boolean isActive() {
        return activeFlag;
    }

    @Override
    public void setActive(boolean activeFlag) {
        this.activeFlag = activeFlag;
    }

    @Override
    public ID getId() {
        return id;
    }

    public void setId(ID id) {
        this.id = id;
    }
}

