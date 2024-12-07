// THIS FILE IS GENERATED FROM MINECRAFT DATA, DO NOT EDIT
// https://github.com/Cosmorise/Passport/generator/src/main/java/me/combimagnetron/passport/generator/BlockGenerator.java
package me.combimagnetron.generated.R1_21.packet.play;
import me.combimagnetron.passport.internal.network.ByteBuffer;
import me.combimagnetron.passport.internal.network.packet.Type;
import me.combimagnetron.passport.util.*;
 
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
 
public class ClientUpdateTime implements me.combimagnetron.passport.internal.network.packet.ClientPacket {
    private final ByteBuffer byteBuffer;
    private final long world_age;
    private final long time_of_day;

    public static ClientUpdateTime updateTime(long world_age, long time_of_day) {
        return new ClientUpdateTime(world_age, time_of_day);
    }

    private ClientUpdateTime(long world_age, long time_of_day) {
        this.byteBuffer = ByteBuffer.empty();
        this.world_age = world_age;
        this.time_of_day = time_of_day;
    }

    private ClientUpdateTime(ByteBuffer buffer) {
        this.byteBuffer = buffer;
        this.world_age = byteBuffer.read(ByteBuffer.Adapter.LONG);
        this.time_of_day = byteBuffer.read(ByteBuffer.Adapter.LONG);
    }

    public long world_age() {
        return world_age;
    }

    public long time_of_day() {
        return time_of_day;
    }

    @Override
    public ByteBuffer byteBuffer() {
        return byteBuffer;
    }

    @Override
    public byte[] write() {
        byteBuffer.write(ByteBuffer.Adapter.LONG, world_age);
        byteBuffer.write(ByteBuffer.Adapter.LONG, time_of_day);
        return byteBuffer.bytes();
    }
}