package com.snowcattle.game.excutor.update;

/**
 * Created by jwp on 2017/1/19.
 */
public class AbstractUpdate implements IUpdate {

    private boolean activeFlag=true;
    private long id;

    @Override
    public void update() {

    }

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
}
