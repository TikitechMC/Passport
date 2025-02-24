package me.combimagnetron.passport.internal.entity.metadata.type;

import com.github.retrooper.packetevents.util.Vector3i;
import me.combimagnetron.passport.internal.network.ByteBuffer;

public record Position(int x, int y, int z) implements MetadataType<Vector3i> {

    public static Position of(int x, int y, int z) {
        return new Position(x, y, z);
    }

    @Override
    public byte[] bytes() {
        final ByteBuffer buffer = ByteBuffer.empty();
        buffer.write(ByteBuffer.Adapter.INT, ((x & 0x3FFFFFF) << 6) | ((z & 0x3FFFFFF) << 12) | (y & 0xFFF));
        return buffer.bytes();
    }

    @Override
    public Vector3i object() {
        return new Vector3i(x, y, z);
    }
}
