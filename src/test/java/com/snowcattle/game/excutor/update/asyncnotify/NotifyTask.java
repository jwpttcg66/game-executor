package com.snowcattle.game.excutor.update.asyncnotify;

import com.snowcattle.game.excutor.service.UpdateService;

import java.util.TimerTask;

/**
 * Created by jwp on 2017/3/28.
 */
public class NotifyTask extends TimerTask {

    private UpdateService updateService;

    public NotifyTask(UpdateService updateService) {
        this.updateService = updateService;
    }

    @Override
    public void run() {
        updateService.notifyRun();
    }
}
