package com.snowcattle.game.excutor.update.async;

import com.snowcattle.game.excutor.entity.AbstractUpdate;
import com.snowcattle.game.excutor.utils.Loggers;

/**
 * Created by jiangwenping on 17/1/16.
 */
public class IntegerUpdate extends AbstractUpdate<Long> {

    private long incrId;

    private  long maxSize;
    private  long startTime;
    public IntegerUpdate(long id, long maxSize) {
        setId(id);
        this.maxSize = maxSize;
        this.startTime = System.currentTimeMillis();
    }

    @Override
    public void update() {
        incrId++;
        long difference  = incrId;
        if (difference == maxSize) {
            setActive(false);
            long endTime = System.currentTimeMillis();
            long useTime = endTime - startTime;
            System.out.println("耗时" + useTime);
        }
        if (Loggers.utilLogger.isDebugEnabled()) {
            Loggers.utilLogger.debug("update id " + getId() + " incrId:" + incrId);
        }
    }

    public String toString() {
        return String.valueOf(this.getId());
    }
}
