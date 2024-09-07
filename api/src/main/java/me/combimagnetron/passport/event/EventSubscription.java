package me.combimagnetron.passport.event;

import me.combimagnetron.passport.event.impl.internal.PacketEvent;
import me.combimagnetron.passport.internal.network.packet.Packet;

import java.util.function.Consumer;

sealed public interface EventSubscription<V extends Event> permits EventSubscription.Impl, EventSubscription.FilteredImpl, EventSubscription.PacketImpl {

    Consumer<? super V> handler();

    Class<V> getEventClass();

    default void close() {
        Dispatcher.dispatcher().manager().unsubscribe(this);
    }

    default boolean active() {
        return Dispatcher.dispatcher().manager().subscriptions().contains(this);
    }

    final class Impl<V extends Event> implements EventSubscription<V> {
        private final Class<V> eventClass;
        private final Consumer<? super V> handler;

        Impl(Class<V> eventClass, Consumer<? super V> handler) {
            this.eventClass = eventClass;
            this.handler = handler;
            if (eventClass.isAssignableFrom(Event.FilteredEvent.class)) {
                throw new IllegalArgumentException("FilteredEvent implementations can only be used with EventFilters present!");
            }
        }

        @Override
        public Consumer<? super V> handler() {
            return handler;
        }

        @Override
        public Class<V> getEventClass() {
            return eventClass;
        }
    }

    final class FilteredImpl<V extends Event> implements EventSubscription<V> {
        private final Class<V> eventClass;
        private final Consumer<? super V> handler;

        FilteredImpl(Class<V> eventClass, EventFilter filter, Consumer<? super V> handler) {
            this.eventClass = eventClass;
            this.handler = handler;
        }

        @Override
        public Consumer<? super V> handler() {
            return handler;
        }

        @Override
        public Class<V> getEventClass() {
            return eventClass;
        }

    }

    final class PacketImpl<P extends Packet, V extends Event> implements EventSubscription<V> {
        private final Class<P> eventClass;
        private final Consumer<? super V> handler;

        PacketImpl(Class<P> eventClass, Consumer<? super V> handler) {
            this.eventClass = eventClass;
            this.handler = handler;
        }

        @Override
        public Consumer<? super V> handler() {
            return handler;
        }

        @Override
        public Class<V> getEventClass() {
            return (Class<V>) PacketEvent.class;
        }

    }

}
