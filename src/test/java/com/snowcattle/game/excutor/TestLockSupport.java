package com.snowcattle.game.excutor;

import java.util.concurrent.locks.LockSupport;

/**
 * Created by jiangwenping on 17/1/6.
 */
public class TestLockSupport {
    public static void main(String[] args) {

        System.out.println("unpark start");
        LockSupport.unpark(Thread.currentThread());
//        LockSupport.unpark(Thread.currentThread());
        System.out.println("park1");
        LockSupport.park();

//        System.out.println("park2");
//        LockSupport.park();
        System.out.println("running");
    }
}
