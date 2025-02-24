// THIS FILE IS GENERATED FROM MINECRAFT DATA, DO NOT EDIT
// https://github.com/Cosmorise/Passport/generator/src/main/java/me/combimagnetron/passport/generator/BlockGenerator.java
package me.combimagnetron.generated.R1_21.packet.play;
import java.lang.Enum;
import me.combimagnetron.passport.internal.network.ByteBuffer;
import me.combimagnetron.passport.internal.network.packet.Type;
import me.combimagnetron.passport.util.*;
 
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
 
public class ServerSwingArm implements me.combimagnetron.passport.internal.network.packet.ServerPacket {
    private final static int ID = 0x36;
    private final ByteBuffer byteBuffer;
    private final Enum hand;

    public static ServerSwingArm swingArm(Enum hand) {
        return new ServerSwingArm(hand);
    }

    private ServerSwingArm(Enum hand) {
        this.byteBuffer = ByteBuffer.empty();
        this.hand = hand;
    }

    private ServerSwingArm(ByteBuffer buffer) {
        this.byteBuffer = buffer;
        this.hand = byteBuffer.read(ByteBuffer.Adapter.ENUM);
    }

    public Enum hand() {
        return hand;
    }

    @Override
    public ByteBuffer byteBuffer() {
        return byteBuffer;
    }

    @Override
    public byte[] write() {
        byteBuffer.write(ByteBuffer.Adapter.VAR_INT, ID);
        byteBuffer.write(ByteBuffer.Adapter.ENUM, hand);
        return byteBuffer.bytes();
    }
}