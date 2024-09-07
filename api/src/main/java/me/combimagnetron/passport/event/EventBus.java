package me.combimagnetron.passport.event;

import me.combimagnetron.passport.event.impl.internal.PacketEvent;
import me.combimagnetron.passport.internal.network.packet.Packet;

import java.util.function.Consumer;

public interface EventBus {

    static <T extends Event> EventSubscription<T> subscribe(Class<T> clazz, EventFilter<T, ?> filter, Consumer<? super T> consumer) {
        return new EventSubscription.FilteredImpl<>(clazz, filter, consumer);
    }

    static <T extends Event> EventSubscription<T> subscribe(Class<T> clazz, Consumer<? super T> consumer) {
        return new EventSubscription.Impl<>(clazz, consumer);
    }

    static <P extends Packet, T extends PacketEvent<P>> EventSubscription<T> packet(Class<P> clazz, Consumer<? super T> consumer) {
        return new EventSubscription.PacketImpl<>(clazz, consumer);
    }

}
