package me.combimagnetron.passport.internal.entity.metadata.type;

import me.combimagnetron.passport.internal.network.ByteBuffer;

public record Boolean(boolean val) implements MetadataType<java.lang.Boolean> {

    public static Boolean of(boolean val) {
        return new Boolean(val);
    }

    @Override
    public byte[] bytes() {
        final ByteBuffer buffer = ByteBuffer.empty();
        buffer.write(ByteBuffer.Adapter.BOOLEAN, val);
        return buffer.bytes();
    }

    @Override
    public java.lang.Boolean object() {
        return val;
    }

}
