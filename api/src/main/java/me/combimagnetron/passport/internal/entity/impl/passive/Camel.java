package me.combimagnetron.passport.internal.entity.impl.passive;

import com.github.retrooper.packetevents.protocol.entity.type.EntityTypes;
import me.combimagnetron.passport.util.data.Identifier;
import me.combimagnetron.passport.internal.entity.impl.passive.horse.BaseHorse;
import me.combimagnetron.passport.internal.entity.metadata.Metadata;
import me.combimagnetron.passport.internal.entity.metadata.type.Boolean;
import me.combimagnetron.passport.internal.entity.metadata.type.VarLong;
import me.combimagnetron.passport.internal.entity.metadata.type.Vector3d;
import me.combimagnetron.passport.util.Pair;

public class Camel extends BaseHorse {
    private boolean dashing = false;

    protected Camel(Vector3d position) {
        super(position);
    }

    public static Camel camel(Vector3d position) {
        return new Camel(position);
    }

    @Override
    public Data data() {
        return Data.of(0);
    }

    @Override
    public Type type() {
        return new Type.Impl(EntityTypes.CAMEL, Identifier.of("minecraft", "camel"), finished());
    }

    @Override
    public Metadata extend() {
        return Metadata.inheritAndMerge(super.extend(), Pair.of(18, Boolean.of(dashing)), Pair.of(19, VarLong.of(0)));
    }

    public boolean dashing() {
        return dashing;
    }

    public void dashing(boolean dashing) {
        this.dashing = dashing;
    }
}
