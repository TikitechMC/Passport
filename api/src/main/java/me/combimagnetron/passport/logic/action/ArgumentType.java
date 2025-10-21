package me.combimagnetron.passport.logic.action;

public record ArgumentType(String name, Class<?> type) {

    public static ArgumentType of(String name, Class<?> type) {
        return new ArgumentType(name, type);
    }

}
