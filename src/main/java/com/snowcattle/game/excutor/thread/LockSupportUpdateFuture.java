package com.snowcattle.game.excutor.thread;

import com.snowcattle.future.AbstractFuture;
import com.snowcattle.future.ITaskFuture;

/**
 * Created by jiangwenping on 17/1/18.
 */
public class LockSupportUpdateFuture extends AbstractFuture<Boolean> {

    @Override
    public ITaskFuture<Boolean> setSuccess(Object result) {
        return super.setSuccess(result);
    }

    @Override
    public ITaskFuture<Boolean> setFailure(Throwable cause) {
        return super.setFailure(cause);
    }
}