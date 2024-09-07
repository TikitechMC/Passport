package me.combimagnetron.passport.event.impl.internal;

import me.combimagnetron.passport.event.Event;
import me.combimagnetron.passport.internal.network.packet.Packet;

public record PacketEvent<P extends Packet>(P packet) implements Event {
    @Override
    public Class<? extends Event> eventType() {
        return PacketEvent.class;
    }
}
