package me.combimagnetron.passport;

import com.github.retrooper.packetevents.PacketEvents;
import com.github.retrooper.packetevents.protocol.entity.data.EntityDataType;
import com.github.retrooper.packetevents.wrapper.PacketWrapper;
import me.combimagnetron.passport.internal.network.Connection;

public class PacketEventsConnectionImpl<T> implements Connection {
    private final T player;

    public PacketEventsConnectionImpl(T player) {
        this.player = player;
    }

    @Override
    public void send(PacketWrapper<?> packetHolder) {
        PacketEvents.getAPI().getPlayerManager().sendPacket(player, packetHolder);
    }
}
