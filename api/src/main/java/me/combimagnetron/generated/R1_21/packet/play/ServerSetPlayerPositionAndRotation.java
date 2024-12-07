// THIS FILE IS GENERATED FROM MINECRAFT DATA, DO NOT EDIT
// https://github.com/Cosmorise/Passport/generator/src/main/java/me/combimagnetron/passport/generator/BlockGenerator.java
package me.combimagnetron.generated.R1_21.packet.play;
import me.combimagnetron.passport.internal.network.ByteBuffer;
import me.combimagnetron.passport.internal.network.packet.Type;
import me.combimagnetron.passport.util.*;
 
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
 
public class ServerSetPlayerPositionAndRotation implements me.combimagnetron.passport.internal.network.packet.ServerPacket {
    private final ByteBuffer byteBuffer;
    private final double x;
    private final double feet_y;
    private final double z;
    private final float yaw;
    private final float pitch;
    private final boolean on_ground;

    public static ServerSetPlayerPositionAndRotation setPlayerPositionAndRotation(double x, double feet_y, double z, float yaw, float pitch, boolean on_ground) {
        return new ServerSetPlayerPositionAndRotation(x, feet_y, z, yaw, pitch, on_ground);
    }

    private ServerSetPlayerPositionAndRotation(double x, double feet_y, double z, float yaw, float pitch, boolean on_ground) {
        this.byteBuffer = ByteBuffer.empty();
        this.x = x;
        this.feet_y = feet_y;
        this.z = z;
        this.yaw = yaw;
        this.pitch = pitch;
        this.on_ground = on_ground;
    }

    private ServerSetPlayerPositionAndRotation(ByteBuffer buffer) {
        this.byteBuffer = buffer;
        this.x = byteBuffer.read(ByteBuffer.Adapter.DOUBLE);
        this.feet_y = byteBuffer.read(ByteBuffer.Adapter.DOUBLE);
        this.z = byteBuffer.read(ByteBuffer.Adapter.DOUBLE);
        this.yaw = byteBuffer.read(ByteBuffer.Adapter.FLOAT);
        this.pitch = byteBuffer.read(ByteBuffer.Adapter.FLOAT);
        this.on_ground = byteBuffer.read(ByteBuffer.Adapter.BOOLEAN);
    }

    public double x() {
        return x;
    }

    public double feet_y() {
        return feet_y;
    }

    public double z() {
        return z;
    }

    public float yaw() {
        return yaw;
    }

    public float pitch() {
        return pitch;
    }

    public boolean on_ground() {
        return on_ground;
    }

    @Override
    public ByteBuffer byteBuffer() {
        return byteBuffer;
    }

    @Override
    public byte[] write() {
        byteBuffer.write(ByteBuffer.Adapter.DOUBLE, x);
        byteBuffer.write(ByteBuffer.Adapter.DOUBLE, feet_y);
        byteBuffer.write(ByteBuffer.Adapter.DOUBLE, z);
        byteBuffer.write(ByteBuffer.Adapter.FLOAT, yaw);
        byteBuffer.write(ByteBuffer.Adapter.FLOAT, pitch);
        byteBuffer.write(ByteBuffer.Adapter.BOOLEAN, on_ground);
        return byteBuffer.bytes();
    }
}