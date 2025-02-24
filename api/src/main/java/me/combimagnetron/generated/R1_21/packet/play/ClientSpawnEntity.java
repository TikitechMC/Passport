// THIS FILE IS GENERATED FROM MINECRAFT DATA, DO NOT EDIT
// https://github.com/Cosmorise/Passport/generator/src/main/java/me/combimagnetron/passport/generator/BlockGenerator.java
package me.combimagnetron.generated.R1_21.packet.play;
import java.lang.Integer;
import java.util.UUID;
import java.lang.Integer;
import java.lang.Integer;
import me.combimagnetron.passport.internal.network.ByteBuffer;
import me.combimagnetron.passport.internal.network.packet.Type;
import me.combimagnetron.passport.util.*;
 
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
 
public class ClientSpawnEntity implements me.combimagnetron.passport.internal.network.packet.ClientPacket {
    private final static int ID = 0x01;
    private final ByteBuffer byteBuffer;
    private final Integer entity_id;
    private final UUID entity_uuid;
    private final Integer type;
    private final double x;
    private final double y;
    private final double z;
    private final byte pitch;
    private final byte yaw;
    private final byte head_yaw;
    private final Integer data;
    private final short velocity_x;

    public static ClientSpawnEntity spawnEntity(Integer entity_id, UUID entity_uuid, Integer type, double x, double y, double z, byte pitch, byte yaw, byte head_yaw, Integer data, short velocity_x) {
        return new ClientSpawnEntity(entity_id, entity_uuid, type, x, y, z, pitch, yaw, head_yaw, data, velocity_x);
    }

    private ClientSpawnEntity(Integer entity_id, UUID entity_uuid, Integer type, double x, double y, double z, byte pitch, byte yaw, byte head_yaw, Integer data, short velocity_x) {
        this.byteBuffer = ByteBuffer.empty();
        this.entity_id = entity_id;
        this.entity_uuid = entity_uuid;
        this.type = type;
        this.x = x;
        this.y = y;
        this.z = z;
        this.pitch = pitch;
        this.yaw = yaw;
        this.head_yaw = head_yaw;
        this.data = data;
        this.velocity_x = velocity_x;
    }

    private ClientSpawnEntity(ByteBuffer buffer) {
        this.byteBuffer = buffer;
        this.entity_id = byteBuffer.read(ByteBuffer.Adapter.VAR_INT);
        this.entity_uuid = byteBuffer.read(ByteBuffer.Adapter.UUID);
        this.type = byteBuffer.read(ByteBuffer.Adapter.VAR_INT);
        this.x = byteBuffer.read(ByteBuffer.Adapter.DOUBLE);
        this.y = byteBuffer.read(ByteBuffer.Adapter.DOUBLE);
        this.z = byteBuffer.read(ByteBuffer.Adapter.DOUBLE);
        this.pitch = byteBuffer.read(ByteBuffer.Adapter.BYTE);
        this.yaw = byteBuffer.read(ByteBuffer.Adapter.BYTE);
        this.head_yaw = byteBuffer.read(ByteBuffer.Adapter.BYTE);
        this.data = byteBuffer.read(ByteBuffer.Adapter.VAR_INT);
        this.velocity_x = byteBuffer.read(ByteBuffer.Adapter.SHORT);
    }

    public Integer entity_id() {
        return entity_id;
    }

    public UUID entity_uuid() {
        return entity_uuid;
    }

    public Integer type() {
        return type;
    }

    public double x() {
        return x;
    }

    public double y() {
        return y;
    }

    public double z() {
        return z;
    }

    public byte pitch() {
        return pitch;
    }

    public byte yaw() {
        return yaw;
    }

    public byte head_yaw() {
        return head_yaw;
    }

    public Integer data() {
        return data;
    }

    public short velocity_x() {
        return velocity_x;
    }

    @Override
    public ByteBuffer byteBuffer() {
        return byteBuffer;
    }

    @Override
    public byte[] write() {
        byteBuffer.write(ByteBuffer.Adapter.VAR_INT, ID);
        byteBuffer.write(ByteBuffer.Adapter.VAR_INT, entity_id);
        byteBuffer.write(ByteBuffer.Adapter.UUID, entity_uuid);
        byteBuffer.write(ByteBuffer.Adapter.VAR_INT, type);
        byteBuffer.write(ByteBuffer.Adapter.DOUBLE, x);
        byteBuffer.write(ByteBuffer.Adapter.DOUBLE, y);
        byteBuffer.write(ByteBuffer.Adapter.DOUBLE, z);
        byteBuffer.write(ByteBuffer.Adapter.BYTE, pitch);
        byteBuffer.write(ByteBuffer.Adapter.BYTE, yaw);
        byteBuffer.write(ByteBuffer.Adapter.BYTE, head_yaw);
        byteBuffer.write(ByteBuffer.Adapter.VAR_INT, data);
        byteBuffer.write(ByteBuffer.Adapter.SHORT, velocity_x);
        return byteBuffer.bytes();
    }
}