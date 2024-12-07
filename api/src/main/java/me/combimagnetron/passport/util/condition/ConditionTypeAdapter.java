package me.combimagnetron.passport.util.condition;

import me.combimagnetron.passport.Passport;
import me.combimagnetron.passport.user.User;

import java.lang.reflect.Field;
import java.util.UUID;
import java.util.function.Function;

public record ConditionTypeAdapter<V>(Function<String, V> get) {
    public static final ConditionTypeAdapter<UUID> UUID = of(java.util.UUID::fromString);
    public static final ConditionTypeAdapter<Double> DOUBLE = of(Double::parseDouble);
    public static final ConditionTypeAdapter<Integer> INT = of(Integer::parseInt);
    public static final ConditionTypeAdapter<Float> FLOAT = of(Float::parseFloat);
    public static final ConditionTypeAdapter<Short> SHORT = of(Short::parseShort);
    public static final ConditionTypeAdapter<Boolean> BOOLEAN = of(Boolean::parseBoolean);
    public static final ConditionTypeAdapter<User<?>> USER = of(s -> Passport.passport().users().users().stream().filter(user -> user.name().equals(s)).findAny().orElse(null));
    public static final ConditionTypeAdapter<String> STRING = of(s -> s);

    public static <V> ConditionTypeAdapter<V> of(Function<String, V> retrieve) {
        return new ConditionTypeAdapter<>(retrieve);
    }

    public static <V> ConditionTypeAdapter<V> find(Class<V> clazz) {
        String name = clazz.getName().toUpperCase();
        Field field;
        try {
            field = ConditionTypeAdapter.class.getField(name);
            return (ConditionTypeAdapter<V>) field.get(null);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}