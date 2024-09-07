package me.combimagnetron.passport.event;

import java.util.function.Function;

sealed public interface EventFilter<V extends Event, H> permits EventFilter.Impl, EventFilter.SpigotImpl {


    static <V extends Event, H> EventFilter<V, H> filter(Class<V> type, Class<H> handler, Function<V, H> getter) {
        return new Impl<>(type, handler, getter);
    }

    H handler(V event);

    Class<V> type();

    Class<H> handlerType();

    final class Impl<V extends Event, H> implements EventFilter<V, H> {
        private final Class<V> type;
        private final Class<H> handler;
        private final Function<V, H> getter;

        Impl(Class<V> type, Class<H> handler, Function<V, H> getter) {
            this.type = type;
            this.handler = handler;
            this.getter = getter;
        }


        @Override
        public H handler(V event) {
            return getter.apply(event);
        }

        @Override
        public Class<V> type() {
            return type;
        }

        @Override
        public Class<H> handlerType() {
            return handler;
        }
    }

    final class SpigotImpl<V extends Event, H> implements EventFilter<V, H> {

        @Override
        public H handler(V event) {
            return null;
        }

        @Override
        public Class<V> type() {
            return null;
        }

        @Override
        public Class<H> handlerType() {
            return null;
        }
    }

}
