package me.combimagnetron.passport.internal.entity.metadata.type;

import me.combimagnetron.passport.internal.network.ByteBuffer;

public record VarLong(long val) implements MetadataType<Long> {

    public static VarLong of(long val) {
        return new VarLong(val);
    }

    @Override
    public byte[] bytes() {
        final ByteBuffer buffer = ByteBuffer.empty();
        buffer.write(ByteBuffer.Adapter.VAR_LONG, val);
        return buffer.bytes();
    }

    @Override
    public Long object() {
        return val;
    }
}
