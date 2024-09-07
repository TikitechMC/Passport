package me.combimagnetron.passport.util;

public record Pair<T, K>(T first, K second) {
    public static <T,K> Pair<T, K> of(T t, K k) {
        return new Pair<>(t, k);
    }

    public boolean firstNull() {
        return first == null;
    }

    public boolean secondNull() {
        return second == null;
    }

    public boolean pairNull() {
        return first == null && second == null;
    }

}
