package me.combimagnetron.passport.internal.entity.metadata.type;

import com.github.retrooper.packetevents.util.Vector3i;
import me.combimagnetron.passport.internal.network.ByteBuffer;
import org.jetbrains.annotations.Nullable;

public record OptPosition(@Nullable Position position) implements MetadataType<Vector3i> {
    @Override
    public byte[] bytes() {
        final ByteBuffer buffer = ByteBuffer.empty();
        final boolean present = position != null;
        buffer.write(ByteBuffer.Adapter.BOOLEAN, present);
        if (present) {
            buffer.nio().put(position.bytes());
        }
        return buffer.bytes();
    }

    @Override
    public Vector3i object() {
        return new Vector3i(position.x(), position.y(), position.z());
    }
}
