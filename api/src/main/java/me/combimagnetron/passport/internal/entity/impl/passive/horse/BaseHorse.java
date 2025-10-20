package me.combimagnetron.passport.internal.entity.impl.passive.horse;

import me.combimagnetron.passport.internal.entity.impl.passive.Animal;
import me.combimagnetron.passport.internal.entity.metadata.Metadata;
import me.combimagnetron.passport.internal.entity.metadata.type.Byte;
import me.combimagnetron.passport.internal.entity.metadata.type.Vector3d;
import me.combimagnetron.passport.util.Pair;

public abstract class BaseHorse extends Animal {
    private byte options = 0;

    public BaseHorse(Vector3d position) {
        super(position);
    }

    @Override
    public Metadata extend() {
        return Metadata.inheritAndMerge(super.extend(), Pair.of(17, Byte.of(options)));
    }

    public void tamed(boolean bool) {
        options = (byte) ((options & ~0x02) | (bool ? 0x02 : 0));
    }

    public void saddled(boolean bool) {
        options = (byte) ((options & ~0x04 | (bool ? 0x04 : 0)));
    }

    public void bred(boolean bool) {
        options = (byte) ((options & ~0x08) | (bool ? 0x08 : 0));
    }

    public void eating(boolean bool) {
        options = (byte) ((options & ~0x10) | (bool ? 0x10 : 0));
    }

    public void rearing(boolean bool) {
        options = (byte) ((options & ~0x20) | (bool ? 0x20 : 0));
    }

    public void mouthOpen(boolean bool) {
        options = (byte) ((options & ~0x40) | (bool ? 0x40 : 0));
    }

    protected void flag(int i, boolean flag) {
        byte b0 = options;
        if (flag) {
            b0 = (byte) (b0 | i);
        } else {
            b0 = (byte) (b0 & ~i);
        }
        this.options = b0;
    }

}
