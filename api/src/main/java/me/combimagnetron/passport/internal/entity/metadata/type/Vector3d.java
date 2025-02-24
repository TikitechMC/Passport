package me.combimagnetron.passport.internal.entity.metadata.type;

import com.github.retrooper.packetevents.util.Vector3f;
import me.combimagnetron.passport.internal.network.ByteBuffer;

public record Vector3d(double x, double y, double z) implements MetadataType<Vector3f> {

    public static Vector3d vec3(double x, double y, double z) {
        return new Vector3d(x, y, z);
    }

    public static Vector3d vec3(double all) {
        return new Vector3d(all, all, all);
    }

    @Override
    public byte[] bytes() {
        final ByteBuffer buffer = ByteBuffer.empty();
        buffer.write(ByteBuffer.Adapter.DOUBLE, x)
                .write(ByteBuffer.Adapter.DOUBLE, y)
                .write(ByteBuffer.Adapter.DOUBLE, z);
        return buffer.bytes();
    }

    @Override
    public Vector3f object() {
        return new Vector3f((float) x, (float) y, (float) z);
    }
}
