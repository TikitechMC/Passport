package me.combimagnetron.passport.internal.entity.metadata.type;

import com.github.retrooper.packetevents.util.Quaternion4f;
import me.combimagnetron.passport.internal.network.ByteBuffer;

public record Quaternion(float x, float y, float z, float w) implements MetadataType<Quaternion4f> {

    public static Quaternion of(float x, float y, float z, float w) {
        return new Quaternion(x, y, z, w);
    }

    @Override
    public byte[] bytes() {
        final ByteBuffer buffer = ByteBuffer.empty();
        buffer.write(ByteBuffer.Adapter.FLOAT, x)
                .write(ByteBuffer.Adapter.FLOAT, y)
                .write(ByteBuffer.Adapter.FLOAT, z)
                .write(ByteBuffer.Adapter.FLOAT, w);
        return buffer.bytes();
    }

    @Override
    public Quaternion4f object() {
        return new Quaternion4f(x, y, z, w);
    }
}
