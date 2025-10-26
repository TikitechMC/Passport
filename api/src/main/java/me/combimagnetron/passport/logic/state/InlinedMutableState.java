package me.combimagnetron.passport.logic.state;

import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public interface InlinedMutableState<T, R> extends MutableState<T> {

    @NotNull InlinedMutableState<T, R> returns(R returningObject);

    @NotNull R modify(Consumer<InlinedMutableState<T, R>> consumer);

    class ObservableInlinedMutableState<T, R> extends ObservableMutableState<T> implements InlinedMutableState<T, R> {
        private R returningObject;

        ObservableInlinedMutableState(T current, ObservableStateChangeCallback<T> callback) {
            super(current, callback);
        }

        @Override
        public @NotNull InlinedMutableState<T, R> returns(R returningObject) {
            this.returningObject = returningObject;
            return this;
        }

        @Override
        public @NotNull R modify(Consumer<InlinedMutableState<T, R>> consumer) {
            consumer.accept(this);
            return returningObject;
        }

    }

}
