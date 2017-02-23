package com.snowcattle.game.excutor.pool;

import com.snowcattle.game.excutor.pool.excutor.SingleThreadEventExecutor;

/**
 * Created by jwp on 2017/2/23.
 */
public class UpdateEventExcutorService {

    private int excutorSize;

    private SingleThreadEventExecutor[] singleThreadEventExecutors;

    public UpdateEventExcutorService(int excutorSize) {
        this.excutorSize = excutorSize;
    }

}
