package me.combimagnetron.passport.internal.entity.impl;

import me.combimagnetron.passport.internal.entity.metadata.type.Vector3d;

public abstract class Creature extends Mob {
    public Creature(Vector3d position) {
        super(position);
    }
}
