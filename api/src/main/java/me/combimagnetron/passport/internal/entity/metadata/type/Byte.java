package me.combimagnetron.passport.internal.entity.metadata.type;

import me.combimagnetron.passport.internal.network.ByteBuffer;

public record Byte(byte val) implements MetadataType<java.lang.Byte> {

    public static Byte of(byte val) {
        return new Byte(val);
    }

    @Override
    public byte[] bytes() {
        final ByteBuffer buffer = ByteBuffer.empty();
        buffer.write(ByteBuffer.Adapter.BYTE, val);
        return buffer.bytes();
    }

    @Override
    public java.lang.Byte object() {
        return val;
    }
}
