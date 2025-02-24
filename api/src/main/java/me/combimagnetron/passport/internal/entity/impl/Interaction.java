package me.combimagnetron.passport.internal.entity.impl;

import me.combimagnetron.passport.internal.entity.Entity;
import me.combimagnetron.passport.internal.entity.metadata.Metadata;
import me.combimagnetron.passport.internal.entity.metadata.type.Boolean;
import me.combimagnetron.passport.internal.entity.metadata.type.Float;
import me.combimagnetron.passport.internal.entity.metadata.type.Vector3d;
import me.combimagnetron.passport.util.Pair;

public class Interaction extends Entity.AbstractEntity {
    private float width;
    private float height;
    private boolean responsive;

    public Interaction(Vector3d position) {
        super(position);
    }

    @Override
    public Vector3d position() {
        return null;
    }

    @Override
    public Vector3d rotation() {
        return null;
    }

    @Override
    public Vector3d velocity() {
        return null;
    }

    @Override
    public Data data() {
        return null;
    }

    @Override
    public Type type() {
        return null;
    }

    @Override
    public Metadata extend() {
        return Metadata.of(
                Pair.of(8, Float.of(width)),
                Pair.of(9, Float.of(height)),
                Pair.of(10, Boolean.of(responsive))
        );
    }
}
