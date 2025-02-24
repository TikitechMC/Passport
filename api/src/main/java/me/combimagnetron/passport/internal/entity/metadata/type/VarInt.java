package me.combimagnetron.passport.internal.entity.metadata.type;

import me.combimagnetron.passport.internal.network.ByteBuffer;

public record VarInt(int val) implements MetadataType<Integer> {

    public static VarInt of(int val) {
        return new VarInt(val);
    }

    @Override
    public byte[] bytes() {
        final ByteBuffer buffer = ByteBuffer.empty();
        //buffer.write(ByteBuffer.Adapter.VAR_INT, val);
        return buffer.bytes();
    }

    @Override
    public Integer object() {
        return val;
    }

}
