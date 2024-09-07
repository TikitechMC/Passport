package me.combimagnetron.passport.util.condition;

import java.util.Arrays;
import java.util.stream.Collectors;

public record Supplier<T>(T value) {

    public static <T> Supplier<T> of(T value) {
        return new Supplier<>(value);
    }

    public static Supplier<?>[] of(Object... values) {
        return Arrays.stream(values).map(Supplier::of).collect(Collectors.toUnmodifiableSet()).toArray(Supplier[]::new);
    }

}
