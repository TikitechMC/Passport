package me.combimagnetron.passport.internal.network.packet.client;

import me.combimagnetron.comet.game.menu.Pos2D;
import me.combimagnetron.passport.internal.entity.Entity;
import me.combimagnetron.passport.internal.entity.metadata.type.Vector3d;
import me.combimagnetron.passport.internal.network.ByteBuffer;
import me.combimagnetron.passport.internal.network.packet.ClientPacket;

public class ClientTeleportEntity implements ClientPacket {
    private final ByteBuffer byteBuffer;
    private final Entity.EntityId entityId;
    private final Vector3d position;
    private final Pos2D rotation;
    private final boolean onGround;

    public static ClientTeleportEntity teleportEntity(Entity entity, Vector3d position, Pos2D rotation, boolean onGround) {
        return new ClientTeleportEntity(entity, position, rotation, onGround);
    }

    private ClientTeleportEntity(Entity entity, Vector3d position, Pos2D rotation, boolean onGround) {
        this.byteBuffer = ByteBuffer.empty();
        this.entityId = entity.id();
        this.position = position;
        this.rotation = rotation;
        this.onGround = onGround;
    }

    private ClientTeleportEntity(ByteBuffer byteBuffer) {
        this.byteBuffer = byteBuffer;
        this.entityId = Entity.EntityId.of(read(ByteBuffer.Adapter.VAR_INT));
        this.position = Vector3d.vec3(read(ByteBuffer.Adapter.DOUBLE), read(ByteBuffer.Adapter.DOUBLE),  read(ByteBuffer.Adapter.DOUBLE));
        this.rotation = Pos2D.of(read(ByteBuffer.Adapter.FLOAT), read(ByteBuffer.Adapter.FLOAT));
        this.onGround = read(ByteBuffer.Adapter.BOOLEAN);
    }

    @Override
    public ByteBuffer byteBuffer() {
        return byteBuffer;
    }

    @Override
    public byte[] write() {
        write(ByteBuffer.Adapter.VAR_INT, entityId.intValue());
        write(ByteBuffer.Adapter.DOUBLE, position.x())
                .write(ByteBuffer.Adapter.DOUBLE, position.y())
                .write(ByteBuffer.Adapter.DOUBLE, position.z());
        write(ByteBuffer.Adapter.FLOAT,(float) rotation.x())
                .write(ByteBuffer.Adapter.FLOAT,(float) rotation.y());
        write(ByteBuffer.Adapter.BOOLEAN, onGround);
        return byteBuffer().bytes();
    }

    public boolean onGround() {
        return onGround;
    }

    public Pos2D rotation() {
        return rotation;
    }

    public Vector3d position() {
        return position;
    }

    public Entity.EntityId entityId() {
        return entityId;
    }
}
