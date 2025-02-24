package me.combimagnetron.passport.internal.entity.metadata.type;

import me.combimagnetron.passport.internal.network.ByteBuffer;

public record Rotation(float x, float y, float z) implements MetadataType<com.github.retrooper.packetevents.protocol.world.Rotation> {

    public static Rotation of(float x, float y, float z) {
        return new Rotation(x, y, z);
    }

    @Override
    public byte[] bytes() {
        final ByteBuffer buffer = ByteBuffer.empty();
        buffer.write(ByteBuffer.Adapter.FLOAT, x)
                .write(ByteBuffer.Adapter.FLOAT, y)
                .write(ByteBuffer.Adapter.FLOAT, z);
        return buffer.bytes();
    }

    @Override
    public com.github.retrooper.packetevents.protocol.world.Rotation object() {
        return com.github.retrooper.packetevents.protocol.world.Rotation.NONE;
    }
}
