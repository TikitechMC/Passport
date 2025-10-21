package me.combimagnetron.passport.logic.state;

@FunctionalInterface
public interface ObservableStateChangeCallback<T> {

    void changed(T old, T current);

}
