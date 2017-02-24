package com.snowcattle.game.excutor.pool;

import com.snowcattle.game.excutor.thread.DispatchThread;
import com.snowcattle.game.excutor.update.IUpdate;

/**
 * Created by jwp on 2017/2/23.
 * 执行一个update
 */
public interface IUpdateExcutor {
    public void excutorUpdate(DispatchThread dispatchThread, IUpdate iUpdate, boolean initFlag);
    public void start();
    public void stop();
}
