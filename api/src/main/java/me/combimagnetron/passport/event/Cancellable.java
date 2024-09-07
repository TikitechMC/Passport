package me.combimagnetron.passport.event;

public interface Cancellable {

    boolean cancelled();

    void cancel(boolean bool);

    default void cancel() {
        cancel(true);
    }

}
