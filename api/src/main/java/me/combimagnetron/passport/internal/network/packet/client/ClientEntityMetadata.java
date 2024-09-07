package me.combimagnetron.passport.internal.network.packet.client;

import me.combimagnetron.passport.internal.entity.Entity;
import me.combimagnetron.passport.internal.entity.metadata.Metadata;
import me.combimagnetron.passport.internal.network.ByteBuffer;
import me.combimagnetron.passport.internal.network.packet.ClientPacket;

public class ClientEntityMetadata implements ClientPacket {
    private final ByteBuffer byteBuffer;
    private final Entity.EntityId entityId;
    private final Metadata metadata;

    public static ClientEntityMetadata entityMetadata(Entity entity) {
        return new ClientEntityMetadata(entity);
    }

    public static ClientEntityMetadata from(ByteBuffer byteBuffer) {
        return new ClientEntityMetadata(byteBuffer);
    }

    private ClientEntityMetadata(Entity entity) {
        this.byteBuffer = ByteBuffer.empty();
        this.entityId = entity.id();
        this.metadata = entity.type().metadata();
    }

    private ClientEntityMetadata(ByteBuffer byteBuffer) {
        this.byteBuffer = byteBuffer;
        this.entityId = Entity.EntityId.of(read(ByteBuffer.Adapter.VAR_INT));
        this.metadata = Metadata.FACTORY.of();
    }

    @Override
    public ByteBuffer byteBuffer() {
        return byteBuffer;
    }

    public Entity.EntityId entityId() {
        return entityId;
    }

    public Metadata metadata() {
        return metadata;
    }

    @Override
    public byte[] write() {
        write(ByteBuffer.Adapter.VAR_INT, entityId.intValue());
        byteBuffer.write(metadata.bytes().bytes());
        return byteBuffer.bytes();
    }

}
