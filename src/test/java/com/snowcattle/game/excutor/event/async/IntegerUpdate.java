package com.snowcattle.game.excutor.event.async;

import com.snowcattle.game.excutor.update.IUpdate;

/**
 * Created by jiangwenping on 17/1/16.
 */
public class IntegerUpdate implements IUpdate{

    public IntegerUpdate(long id) {
        this.id = id;
    }

    private long id;

    @Override
    public void update() {
        System.out.println("id" + id + ":update");
    }

    @Override
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String toString(){
        return String.valueOf(this.id);
    }
}
