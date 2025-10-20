package me.combimagnetron.passport.internal.entity;

import me.combimagnetron.passport.internal.entity.impl.Creature;
import me.combimagnetron.passport.internal.entity.metadata.Metadata;
import me.combimagnetron.passport.internal.entity.metadata.type.Boolean;
import me.combimagnetron.passport.internal.entity.metadata.type.Vector3d;
import me.combimagnetron.passport.util.Pair;

public abstract class AgeableMob extends Creature {

    public AgeableMob(Vector3d position) {
        super(position);
    }

    @Override
    public Metadata extend() {
        return Metadata.inheritAndMerge(super.extend(), Pair.of(16, Boolean.of(false)));
    }

}
