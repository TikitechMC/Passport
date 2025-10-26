package me.combimagnetron.passport.logic.state;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface MutableState<T> extends State<T> {

    @NotNull MutableState<T> state(@Nullable T t);

    @Nullable ObservableStateChangeCallback<T> callback();

    default @NotNull ObservableMutableState<T> observe(ObservableStateChangeCallback<T> callback) {
        return new ObservableMutableState<>(value(), callback);
    }

    default <R> @NotNull InlinedMutableState<T, R> inlined() {
        return new InlinedMutableState.ObservableInlinedMutableState<>(value(), callback());
    }

    class SimpleMutableState<T> implements MutableState<T> {
        protected T current;

        SimpleMutableState(T current) {
            this.current = current;
        }

        public SimpleMutableState() {
        }

        @Override
        public @NotNull T value() {
            return current;
        }

        @Override
        public @NotNull MutableState<T> state(T t) {
            this.current = t;
            return this;
        }

        @Override
        public @Nullable ObservableStateChangeCallback<T> callback() {
            return null;
        }
    }

    class ObservableMutableState<T> extends SimpleMutableState<T> {
        private final ObservableStateChangeCallback<T> callback;

        ObservableMutableState(T current, ObservableStateChangeCallback<T> callback) {
            super(current);
            this.callback = callback;
        }

        @Override
        public @Nullable ObservableStateChangeCallback<T> callback() {
            return callback;
        }

        @Override
        public @NotNull MutableState<T> state(T t) {
            callback.changed(current, t);
            super.state(t);
            return this;
        }

    }

}
