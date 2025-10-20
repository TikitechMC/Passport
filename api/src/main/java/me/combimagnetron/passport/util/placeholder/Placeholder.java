package me.combimagnetron.passport.util.placeholder;

public record Placeholder<T>(T player, String placeholder) {

    public static <T> Placeholder<T> of(T player, String placeholder) {
        return new Placeholder<>(player, placeholder);
    }

}
