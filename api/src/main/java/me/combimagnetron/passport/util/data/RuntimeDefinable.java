package me.combimagnetron.passport.util.data;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Function;

@SuppressWarnings("rawtypes")
public interface RuntimeDefinable<T, B extends RuntimeDefinable.Builder, V, L> {

    T build(V var);

    B builder(L l);

    void builder(L l, B builder);

    interface Builder<T, V> {

        int priority();

        Class<?> type();

        T finish(V v);

    }

    interface Holder {

        Collection<Builder<?, ?>> definables();

        void add(Builder<?, ?> definable);

    }

    class Impl<T, B extends Builder, V, L> implements RuntimeDefinable<T, B, V, L> {
        private final Map<L, B> lbMap = new LinkedHashMap<>();
        private final T instance;

        public Impl(Class<? extends RuntimeDefinable<?, ?, ?, ?>> type, Map<L, B> lbMap, T instance) {
            this.lbMap.putAll(lbMap);
            this.instance = instance;
        }

        @Override
        public T build(V var) {
            return instance;
        }

        @Override
        public B builder(L l) {
            return lbMap.get(l);
        }

        @Override
        public void builder(L l, B builder) {
            lbMap.put(l, builder);
        }

        public static class SimpleBuilder<T, V> implements Builder<T, V> {
            private final Class<?> type;
            private final Function<V, T> function;

            public SimpleBuilder(Class<?> type, Function<V, T> function) {
                this.type = type;
                this.function = function;
            }

            @Override
            public int priority() {
                return 0;
            }

            @Override
            public Class<?> type() {
                return type;
            }

            @Override
            public T finish(V o) {
                return function.apply(o);
            }
        }

    }

}
