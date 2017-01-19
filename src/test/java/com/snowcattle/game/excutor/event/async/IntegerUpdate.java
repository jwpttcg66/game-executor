package com.snowcattle.game.excutor.event.async;

import com.snowcattle.game.excutor.update.IUpdate;
import com.snowcattle.game.excutor.utils.Loggers;
import sun.rmi.runtime.Log;

/**
 * Created by jiangwenping on 17/1/16.
 */
public class IntegerUpdate implements IUpdate{

    public IntegerUpdate(long id) {
        this.id = id;
    }

    private long id;

    private long incrId;
    @Override
    public void update() {
        incrId++;
        if(Loggers.utilLogger.isDebugEnabled()){
            Loggers.utilLogger.debug("update id " + id + " incrId:" + incrId);
        }
    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public boolean isActive() {
        return true;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String toString(){
        return String.valueOf(this.id);
    }
}
