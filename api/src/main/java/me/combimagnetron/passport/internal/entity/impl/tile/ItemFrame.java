package me.combimagnetron.passport.internal.entity.impl.tile;

import com.github.retrooper.packetevents.protocol.entity.type.EntityTypes;
import me.combimagnetron.passport.util.data.Identifier;
import me.combimagnetron.passport.internal.entity.Entity;
import me.combimagnetron.passport.internal.entity.metadata.Metadata;
import me.combimagnetron.passport.internal.entity.metadata.type.Slot;
import me.combimagnetron.passport.internal.entity.metadata.type.VarInt;
import me.combimagnetron.passport.internal.entity.metadata.type.Vector3d;
import me.combimagnetron.passport.internal.item.Item;
import me.combimagnetron.passport.util.Pair;

public class ItemFrame extends Entity.AbstractEntity {
    private final Item item;

    public ItemFrame(Vector3d position, Item item) {
        super(position);
        this.item = item;
    }

    @Override
    public Metadata extend() {
        return Metadata.of(Pair.of(8, Slot.of(item)), Pair.of(9, VarInt.of(0)));
    }

    @Override
    public Data data() {
        return Data.of(0);
    }

    @Override
    public Type type() {
        return new Type.Impl(
                EntityTypes.ITEM_DISPLAY,
                Identifier.of("minecraft", "item_frame"),
                this.extend()
        );
    }
}
