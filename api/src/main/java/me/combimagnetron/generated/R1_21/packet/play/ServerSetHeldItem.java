// THIS FILE IS GENERATED FROM MINECRAFT DATA, DO NOT EDIT
// https://github.com/Cosmorise/Passport/generator/src/main/java/me/combimagnetron/passport/generator/BlockGenerator.java
package me.combimagnetron.generated.R1_21.packet.play;
import me.combimagnetron.passport.internal.network.ByteBuffer;
import me.combimagnetron.passport.internal.network.packet.Type;
import me.combimagnetron.passport.util.*;
 
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
 
public class ServerSetHeldItem implements me.combimagnetron.passport.internal.network.packet.ServerPacket {
    private final static int ID = 0x2F;
    private final ByteBuffer byteBuffer;
    private final short slot;

    public static ServerSetHeldItem setHeldItem(short slot) {
        return new ServerSetHeldItem(slot);
    }

    private ServerSetHeldItem(short slot) {
        this.byteBuffer = ByteBuffer.empty();
        this.slot = slot;
    }

    private ServerSetHeldItem(ByteBuffer buffer) {
        this.byteBuffer = buffer;
        this.slot = byteBuffer.read(ByteBuffer.Adapter.SHORT);
    }

    public short slot() {
        return slot;
    }

    @Override
    public ByteBuffer byteBuffer() {
        return byteBuffer;
    }

    @Override
    public byte[] write() {
        byteBuffer.write(ByteBuffer.Adapter.VAR_INT, ID);
        byteBuffer.write(ByteBuffer.Adapter.SHORT, slot);
        return byteBuffer.bytes();
    }
}