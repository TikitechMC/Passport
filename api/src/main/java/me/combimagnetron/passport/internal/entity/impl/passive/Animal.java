package me.combimagnetron.passport.internal.entity.impl.passive;

import me.combimagnetron.passport.internal.entity.AgeableMob;
import me.combimagnetron.passport.internal.entity.metadata.type.Vector3d;

public abstract class Animal extends AgeableMob {
    public Animal(Vector3d position) {
        super(position);
    }
}
