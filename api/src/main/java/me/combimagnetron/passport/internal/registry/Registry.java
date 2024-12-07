package me.combimagnetron.passport.internal.registry;

import me.combimagnetron.passport.data.Identifier;
import me.combimagnetron.passport.util.Pair;

import java.util.Map;

public interface Registry<T> {

    T get(Identifier key);

    T register(Identifier key, T value);

    T unregister(Identifier key);

    boolean contains(Identifier key);

    Pair<Identifier, T> find(T value);

    static <T> Registry<T> empty() {
        return new Impl<>();
    }

    class Impl<T> implements Registry<T> {
        private final Map<Identifier, T> registry = new java.util.HashMap<>();
        private final Map<T, Identifier> reverseRegistry = new java.util.HashMap<>();

        public T get(Identifier key) {
            return registry.get(key);
        }

        public Map<Identifier, T> registry() {
            return registry;
        }

        public Pair<Identifier, T> find(T value) {
            return new Pair<>(reverseRegistry.get(value), value);
        }

        public T register(Identifier key, T value) {
            reverseRegistry.put(value, key);
            return registry.put(key, value);
        }

        public T unregister(Identifier key) {
            reverseRegistry.remove(registry.get(key));
            return registry.remove(key);
        }

        public boolean contains(Identifier key) {
            return registry.containsKey(key);
        }
    }

}
