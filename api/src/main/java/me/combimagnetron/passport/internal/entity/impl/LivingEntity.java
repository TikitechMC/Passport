package me.combimagnetron.passport.internal.entity.impl;

import me.combimagnetron.passport.data.Identifier;
import me.combimagnetron.passport.internal.entity.Entity;
import me.combimagnetron.passport.internal.entity.metadata.Metadata;
import me.combimagnetron.passport.internal.entity.metadata.type.*;
import me.combimagnetron.passport.internal.entity.metadata.type.Boolean;
import me.combimagnetron.passport.internal.entity.metadata.type.Byte;
import me.combimagnetron.passport.internal.entity.metadata.type.Float;
import me.combimagnetron.passport.internal.particle.Particle;
import me.combimagnetron.passport.util.Pair;

import java.util.ArrayList;
import java.util.List;

public abstract class LivingEntity extends Entity.AbstractEntity {
    private byte hand = 0;

    public LivingEntity(Vector3d position) {
        super(position);
    }

    @Override
    public Metadata extend() {
        return Metadata.of(
                Pair.of(8, Byte.of((byte)0)),
                Pair.of(9, Float.of(1)),
                Pair.of(10, ParticleList.of(new ArrayList<>(List.of(Particle.simple(0, Identifier.of("minecraft", "ash")))))),
                Pair.of(11, Boolean.of(false)),
                Pair.of(12, VarInt.of(0)),
                Pair.of(13, VarInt.of(0)),
                Pair.of(14, OptPosition.of(null))
        );
    }

}
