package me.combimagnetron.passport.logic.action.adapter;

import me.combimagnetron.passport.util.data.Identifier;
import me.combimagnetron.passport.util.data.Values;

import java.util.function.Function;

public interface TypeAdapter<T> {
    TypeAdapter<Identifier> IDENTIFIER = of(Identifier.class, Identifier::split);
    TypeAdapter<Integer> INTEGER = of(Integer.class, Integer::parseInt);
    TypeAdapter<Double> DOUBLE = of(Double.class, Double::parseDouble);
    TypeAdapter<Float> FLOAT = of(Float.class, Float::parseFloat);
    TypeAdapter<Long> LONG = of(Long.class, Long::parseLong);
    TypeAdapter<Short> SHORT = of(Short.class, Short::parseShort);
    TypeAdapter<Byte> BYTE = of(Byte.class, Byte::parseByte);
    TypeAdapter<Boolean> BOOLEAN = of(Boolean.class, Boolean::parseBoolean);
    TypeAdapter<String> STRING = of(String.class, string -> string);
    Values<TypeAdapter<?>> VALUES = Values.of(
            IDENTIFIER,
            INTEGER,
            DOUBLE,
            FLOAT,
            LONG,
            SHORT,
            BYTE,
            BOOLEAN,
            STRING
    );

    Class<T> type();

    T adapt(String value);

    static <T> TypeAdapter<T> of(Class<T> type, Function<String, T> function) {
        return new Impl<>(type, function);
    }

    class Impl<T> implements TypeAdapter<T> {
        private final Class<T> type;
        private final Function<String, T> function;

        protected Impl(Class<T> type, Function<String, T> function) {
            this.type = type;
            this.function = function;
        }

        @Override
        public Class<T> type() {
            return type;
        }

        @Override
        public T adapt(String value) {
            return function.apply(value);
        }

    }

}