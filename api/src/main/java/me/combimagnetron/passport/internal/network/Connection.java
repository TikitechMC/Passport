package me.combimagnetron.passport.internal.network;

import com.github.retrooper.packetevents.wrapper.PacketWrapper;
import me.combimagnetron.passport.internal.entity.Entity;
import me.combimagnetron.passport.internal.network.packet.Packet;

public interface Connection {

    void send(PacketWrapper<?> packetHolder);

}