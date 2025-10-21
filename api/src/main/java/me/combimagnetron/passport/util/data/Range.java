package me.combimagnetron.passport.util.data;

public record Range<T extends Comparable<T>>(T min, T max) {

    public static <T extends Comparable<T>> Range<T> of(T min, T max) {
        return new Range<>(min, max);
    }

    public boolean in(T value) {
        return (min == null || min.compareTo(value) <= 0) && (max == null || max.compareTo(value) >= 0);
    }

}
