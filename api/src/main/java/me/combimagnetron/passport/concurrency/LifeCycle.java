package me.combimagnetron.passport.concurrency;

public interface LifeCycle {
    void stop();

    void pause();

    void resume();

}