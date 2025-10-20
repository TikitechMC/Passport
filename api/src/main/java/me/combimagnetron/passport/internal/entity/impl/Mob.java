package me.combimagnetron.passport.internal.entity.impl;

import me.combimagnetron.passport.internal.entity.metadata.Metadata;
import me.combimagnetron.passport.internal.entity.metadata.type.Byte;
import me.combimagnetron.passport.internal.entity.metadata.type.Vector3d;
import me.combimagnetron.passport.util.Pair;

public abstract class Mob extends LivingEntity {

    public Mob(Vector3d position) {
        super(position);
    }

    @Override
    public Metadata extend() {
        return Metadata.inheritAndMerge(super.extend(), Pair.of(15, Byte.of((byte)0)));
    }

}
