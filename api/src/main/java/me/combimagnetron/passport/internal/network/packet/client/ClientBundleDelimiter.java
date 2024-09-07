package me.combimagnetron.passport.internal.network.packet.client;

import me.combimagnetron.passport.internal.network.ByteBuffer;
import me.combimagnetron.passport.internal.network.packet.ClientPacket;
import me.combimagnetron.passport.internal.network.packet.Packet;

import java.util.Collection;
import java.util.List;

public class ClientBundleDelimiter implements ClientPacket {
    private final ByteBuffer byteBuffer = ByteBuffer.empty();
    private final Collection<Packet> containers;

    public static ClientBundleDelimiter bundleDelimiter(Packet... containers) {
        return new ClientBundleDelimiter(List.of(containers));
    }

    public static ClientBundleDelimiter from(ByteBuffer byteBuffer) {
        return new ClientBundleDelimiter(List.of());
    }

    private ClientBundleDelimiter(Collection<Packet> containers) {
        this.containers = containers;
    }

    public Collection<Packet> containers() {
        return this.containers;
    }

    @Override
    public ByteBuffer byteBuffer() {
        return byteBuffer;
    }

    @Override
    public byte[] write() {
        return byteBuffer.bytes();
    }
}
