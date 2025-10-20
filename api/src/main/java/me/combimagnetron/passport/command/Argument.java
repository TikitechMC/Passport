package me.combimagnetron.passport.command;

public interface Argument<T> {

    T value();

    Class<T> type();

}
