package me.combimagnetron.passport.logic.action;

import me.combimagnetron.passport.logic.variable.Variable;

public interface Argument<T> {

    Variable<T> reference();

    String name();

    T value();

    default Class<T> type() {
        return (Class<T>) value().getClass();
    };

    default boolean isEmpty() {
        return value() == null;
    }

    public static <T> Argument<T> of(Variable<T> reference, String name, T value) {
        return new Impl<>(reference, name, value);
    }

    record Impl<T>(Variable<T> reference, String name, T value) implements Argument<T> {
    }

}
