// THIS FILE IS GENERATED FROM MINECRAFT DATA, DO NOT EDIT
// https://github.com/Cosmorise/Passport/generator/src/main/java/me/combimagnetron/passport/generator/BlockGenerator.java
package me.combimagnetron.generated.R1_21.packet.play;
import java.lang.Integer;
import me.combimagnetron.passport.internal.entity.metadata.Metadata;
import me.combimagnetron.passport.internal.network.ByteBuffer;
import me.combimagnetron.passport.internal.network.packet.Type;
import me.combimagnetron.passport.util.*;
 
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
 
public class ClientSetEntityMetadata implements me.combimagnetron.passport.internal.network.packet.ClientPacket {
    private final static int ID = 0x58;
    private final ByteBuffer byteBuffer;
    private final Integer entity_id;
    private final Metadata metadata;

    public static ClientSetEntityMetadata setEntityMetadata(Integer entity_id, Metadata metadata) {
        return new ClientSetEntityMetadata(entity_id, metadata);
    }

    private ClientSetEntityMetadata(Integer entity_id, Metadata metadata) {
        this.byteBuffer = ByteBuffer.empty();
        this.entity_id = entity_id;
        this.metadata = metadata;
    }

    private ClientSetEntityMetadata(ByteBuffer buffer) {
        this.byteBuffer = buffer;
        this.entity_id = byteBuffer.read(ByteBuffer.Adapter.VAR_INT);
        this.metadata = byteBuffer.read(ByteBuffer.Adapter.METADATA);
    }

    public Integer entity_id() {
        return entity_id;
    }

    public Metadata metadata() {
        return metadata;
    }

    @Override
    public ByteBuffer byteBuffer() {
        return byteBuffer;
    }

    @Override
    public byte[] write() {
        byteBuffer.write(ByteBuffer.Adapter.VAR_INT, ID);
        byteBuffer.write(ByteBuffer.Adapter.VAR_INT, entity_id);
        byteBuffer.write(ByteBuffer.Adapter.METADATA, metadata);
        return byteBuffer.bytes();
    }
}