package com.snowcattle.game.excutor.service.excutor;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.*;

/**
 * Created by jwp on 2017/2/23.
 * 单线程
 */
public class SingleThreadEventExecutor extends  FinalizableDelegatedExecutorService implements OrderedEventExecutor{

    SingleThreadEventExecutor() {
        super(new ThreadPoolExecutor(1, 1,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>()));
    }
}
