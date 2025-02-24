package me.combimagnetron.passport.internal.entity.metadata.type;

import me.combimagnetron.passport.internal.network.ByteBuffer;

public record Float(float val) implements MetadataType<java.lang.Float> {

    public static Float of(float val) {
        return new Float(val);
    }

    @Override
    public byte[] bytes() {
        final ByteBuffer buffer = ByteBuffer.empty();
        buffer.write(ByteBuffer.Adapter.FLOAT, val);
        return buffer.bytes();
    }

    @Override
    public java.lang.Float object() {
        return val;
    }
}
