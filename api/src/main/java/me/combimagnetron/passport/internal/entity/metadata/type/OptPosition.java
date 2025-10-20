package me.combimagnetron.passport.internal.entity.metadata.type;

import com.github.retrooper.packetevents.util.Vector3i;
import me.combimagnetron.passport.internal.network.ByteBuffer;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public record OptPosition(@Nullable Position position) implements MetadataType<Optional<Vector3i>> {
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

    public static OptPosition of(@Nullable Position position) {
        return new OptPosition(position);
    }

    @Override
    public Optional<Vector3i> object() {
        var obj = position == null ? null : new Vector3i(position.x(), position.y(), position.z());
        return Optional.ofNullable(obj);
    }
}
