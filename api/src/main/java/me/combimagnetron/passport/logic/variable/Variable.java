package me.combimagnetron.passport.logic.variable;

import me.combimagnetron.passport.util.data.Identifier;

public interface Variable<T> {

    Identifier identifier();

    T value();

    void update();

    Class<T> type();

}
