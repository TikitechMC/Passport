package me.combimagnetron.passport.logic.state;

public interface State<T> {

    T value();

    static <T> MutableState<T> mutable(T initial) {
        return new MutableState.SimpleMutableState<>(initial);
    }

    static <T, R> InlinedMutableState<T, R> inlined(T initial, ObservableStateChangeCallback<T> callback) {
        return new InlinedMutableState.ObservableInlinedMutableState<>(initial, callback);
    }

}
