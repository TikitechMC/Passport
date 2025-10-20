package me.combimagnetron.passport.internal.entity.impl.passive;

import me.combimagnetron.passport.internal.entity.metadata.Metadata;
import me.combimagnetron.passport.internal.entity.metadata.type.Byte;
import me.combimagnetron.passport.internal.entity.metadata.type.OptLivingEntityRef;
import me.combimagnetron.passport.internal.entity.metadata.type.Vector3d;
import me.combimagnetron.passport.util.Pair;

import java.util.UUID;

public abstract class TameableAnimal extends Animal {
    private byte options = 0;
    private UUID owner;

    public TameableAnimal(Vector3d position) {
        super(position);
    }

    @Override
    public Metadata extend() {
        return Metadata.inheritAndMerge(super.extend(), Pair.of(17, Byte.of(options)), Pair.of(18, OptLivingEntityRef.of(owner)));
    }

    public void tamed(boolean bool) {
        if (bool) {
            options = (byte) (options << 0x04);
        } else {
            options = (byte) (options >> 0x04);
        }
    }

    public void owner(UUID uuid) {
        this.owner = uuid;
    }

}
