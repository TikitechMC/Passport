package me.combimagnetron.passport.util.condition;

import me.combimagnetron.passport.Passport;
import me.combimagnetron.passport.user.User;
import me.combimagnetron.passport.util.Pair;
import me.combimagnetron.passport.util.placeholder.Placeholder;

import java.lang.reflect.Field;
import java.util.UUID;
import java.util.function.BiFunction;
import java.util.function.Function;

public interface ConditionTypeAdapter<V> {
    ConditionTypeAdapter<UUID> UUID = of(((s, o) -> java.util.UUID.fromString(s)));
    ConditionTypeAdapter<Double> DOUBLE = of((s, o) -> Double.parseDouble(s));
    ConditionTypeAdapter<Integer> INT = of((s, o) -> Integer.parseInt(s));
    ConditionTypeAdapter<Float> FLOAT = of((s, o) -> Float.parseFloat(s));
    ConditionTypeAdapter<Short> SHORT = of((s, o) -> Short.parseShort(s));
    ConditionTypeAdapter<Boolean> BOOLEAN = of((s, o) -> Boolean.parseBoolean(s));
    ConditionTypeAdapter<User<?>> USER = of((s, o) -> Passport.passport().users().users().stream().filter(user -> user.name().equals(s)).findAny().orElse(null));
    ConditionTypeAdapter<String> STRING = of((s, o) -> s);

    static <V> ConditionTypeAdapter<V> of(BiFunction<String, Object, V> retrieve) {
        return new Impl<>(retrieve);
    }

    BiFunction<String, Object, V> get();

    static <V> ConditionTypeAdapter<V> find(Class<V> clazz) {
        String name = clazz.getName().toUpperCase();
        Field field;
        try {
            field = ConditionTypeAdapter.class.getField(name);
            return (ConditionTypeAdapter<V>) field.get(null);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    record Impl<V, T>(BiFunction<String, Object, V> get) implements ConditionTypeAdapter<V> {

    }

}