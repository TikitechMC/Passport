package me.combimagnetron.passport.internal.entity.metadata.type;

import me.combimagnetron.passport.internal.network.ByteBuffer;

public record Vector3d(double x, double y, double z) implements MetadataType {

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
}
