package com.snowcattle.game.excutor.event.async;

import com.snowcattle.game.excutor.update.AbstractUpdate;
import com.snowcattle.game.excutor.utils.Loggers;

/**
 * Created by jiangwenping on 17/1/16.
 */
public class IntegerUpdate extends AbstractUpdate {

    public IntegerUpdate(long id) {
        this.id = id;
    }

    private long id;

    private long incrId;

    @Override
    public void update() {
        incrId++;
        if(incrId == 100000){
            setActive(false);
        }
        if(Loggers.utilLogger.isDebugEnabled()){
            Loggers.utilLogger.debug("update id " + id + " incrId:" + incrId);
        }
    }

    @Override
    public long getId() {
        return id;
    }

    public String toString(){
        return String.valueOf(this.id);
    }
}
