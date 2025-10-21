package me.combimagnetron.passport.internal.entity.impl.hostile;

import com.github.retrooper.packetevents.protocol.entity.type.EntityTypes;
import me.combimagnetron.passport.util.data.Identifier;
import me.combimagnetron.passport.internal.entity.Entity;
import me.combimagnetron.passport.internal.entity.metadata.Metadata;
import me.combimagnetron.passport.internal.entity.metadata.type.Vector3d;

public class Husk extends Entity.AbstractEntity {

    Husk(Vector3d position) {
        super(position);
    }

    public static Husk husk(Vector3d position) {
        return new Husk(position);
    }

    @Override
    public Data data() {
        return Data.of(0);
    }

    @Override
    public Type type() {
        return new Type.Impl(EntityTypes.HUSK, Identifier.split("minecraft:husk"), Metadata.of());
    }

    @Override
    public Metadata extend() {
        return Metadata.of();
    }
}
