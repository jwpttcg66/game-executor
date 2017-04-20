package com.snowcattle.game.excutor.update.async;

import com.snowcattle.game.excutor.entity.AbstractUpdate;
import com.snowcattle.game.excutor.utils.Loggers;

/**
 * Created by jiangwenping on 17/1/16.
 */
public class IntegerUpdate extends AbstractUpdate {

    private long incrId;

    public IntegerUpdate(long id) {
        setId(id);
    }

    @Override
    public void update() {
        incrId++;
        if (incrId == 2000) {
            setActive(false);
        }
        if (Loggers.utilLogger.isDebugEnabled()) {
            Loggers.utilLogger.debug("update id " + getId() + " incrId:" + incrId);
        }
    }

    public String toString() {
        return String.valueOf(this.getId());
    }
}
