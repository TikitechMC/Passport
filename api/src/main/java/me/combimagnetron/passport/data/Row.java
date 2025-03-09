package me.combimagnetron.passport.data;

public interface Row<T> {
    Class<T> type();

    String name();

    static <T> Row<T> row(String name, Class<T> type) {
        return new Impl<>(type, name);
    }

    record Impl<T>(Class<T> type, String name) implements Row<T> {

    }

}
