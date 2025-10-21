package me.combimagnetron.passport.logic.variable;

import me.combimagnetron.passport.util.data.Identifier;

public record SimpleVariable<T>(T value, Identifier identifier) implements Variable<T> {

    @Override
    public void update() {
        // No-op
    }

    @Override
    public Class<T> type() {
        return (Class<T>) value.getClass();
    }

}
