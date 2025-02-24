package me.combimagnetron.passport.internal.entity.metadata.type;

import com.github.retrooper.packetevents.protocol.entity.pose.EntityPose;
import me.combimagnetron.passport.internal.network.ByteBuffer;

public record Pose(Value value) implements MetadataType<EntityPose> {

    public static Pose of(Value value) {
        return new Pose(value);
    }

    public enum Value {
        STANDING, FALL_FLYING, SLEEPING, SWIMMING, SPIN_ATTACK, SNEAKING, LONG_JUMPING, DYING, CROAKING, USING_TONGUE, SITTING, ROARING, SNIFFING, EMERGING, DIGGING
    }

    @Override
    public byte[] bytes() {
        final ByteBuffer buffer = ByteBuffer.empty();
        //buffer.write(ByteBuffer.Adapter.VAR_INT, value.ordinal());
        return buffer.bytes();
    }

    @Override
    public EntityPose object() {
        return EntityPose.valueOf(value.name());
    }
}
