package me.combimagnetron.passport.internal.network;

import me.combimagnetron.passport.internal.network.packet.Packet;

public interface Connection {

    void send(Packet packetHolder);

}