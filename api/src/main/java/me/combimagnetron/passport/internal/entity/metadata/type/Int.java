package me.combimagnetron.passport.internal.entity.metadata.type;

import me.combimagnetron.passport.internal.network.ByteBuffer;

public record Int(int val) implements MetadataType<Integer> {

    public static Int of(int val) {
        return new Int(val);
    }

    @Override
    public byte[] bytes() {
        final ByteBuffer buffer = ByteBuffer.empty();
        buffer.write(ByteBuffer.Adapter.INT, val);
        return buffer.bytes();
    }

    @Override
    public Integer object() {
        return val;
    }
}
