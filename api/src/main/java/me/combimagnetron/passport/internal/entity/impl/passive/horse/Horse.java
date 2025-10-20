package me.combimagnetron.passport.internal.entity.impl.passive.horse;

import me.combimagnetron.passport.data.Identifier;
import me.combimagnetron.passport.internal.entity.metadata.Metadata;
import me.combimagnetron.passport.internal.entity.metadata.type.Int;
import me.combimagnetron.passport.internal.entity.metadata.type.VarInt;
import me.combimagnetron.passport.internal.entity.metadata.type.Vector3d;
import me.combimagnetron.passport.util.Pair;

public class Horse extends BaseHorse {
    private Variant variant = Variant.of(Color.WHITE, Marking.BLACK_DOTS);

    protected Horse(Vector3d position) {
        super(position);
    }

    public static Horse horse(Vector3d position) {
        return new Horse(position);
    }

    @Override
    public Metadata extend() {
        return Metadata.inheritAndMerge(super.extend(), Pair.of(18, VarInt.of(variant.id())));
    }

    @Override
    public Data data() {
        return Data.of(0);
    }

    @Override
    public Type type() {
        return new Type.Impl(64, Identifier.of("minecraft", "horse"), finished());
    }

    public void variant(Variant variant) {
        this.variant = variant;
    }

    public Variant variant() {
        return variant;
    }

}
