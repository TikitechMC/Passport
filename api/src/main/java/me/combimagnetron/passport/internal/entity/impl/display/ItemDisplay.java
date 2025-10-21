package me.combimagnetron.passport.internal.entity.impl.display;

import com.github.retrooper.packetevents.protocol.entity.type.EntityTypes;
import me.combimagnetron.generated.R1_21_4.item.Material_1_21_4;
import me.combimagnetron.passport.util.data.Identifier;
import me.combimagnetron.passport.internal.entity.metadata.Metadata;
import me.combimagnetron.passport.internal.entity.metadata.type.Byte;
import me.combimagnetron.passport.internal.entity.metadata.type.Slot;
import me.combimagnetron.passport.internal.entity.metadata.type.Vector3d;
import me.combimagnetron.passport.internal.item.Item;
import me.combimagnetron.passport.internal.item.ItemComponent;
import me.combimagnetron.passport.util.Pair;

import java.util.function.Consumer;

public class ItemDisplay extends Display {
    private Item item;
    private DisplayType displayType = DisplayType.NONE;

    public static ItemDisplay itemDisplay(Vector3d position) {
        return new ItemDisplay(position, display -> {});
    }

    public ItemDisplay(Vector3d position, Consumer<Display> loaded) {
        super(position, loaded);
    }

    @Override
    public Type type() {
        return new Type.Impl(EntityTypes.ITEM_DISPLAY, Identifier.of("minecraft", "item_display"), this.finished());
    }

    public enum DisplayType {
        NONE, THIRD_PERSON_LEFT_HAND, THIRD_PERSON_RIGHT_HAND, FIRST_PERSON_LEFT_HAND, FIRST_PERSON_RIGHT_HAND, HEAD, GUI, GROUND, FIXED
    }

    public Item item() {
        return item;
    }

    public void item(Item item) {
        this.item = item;
    }

    public DisplayType displayType() {
        return displayType;
    }

    public void displayType(DisplayType displayType) {
        this.displayType = displayType;
    }

    @Override
    public Metadata extend() {
        return Metadata.inheritAndMerge(
                base(),
                Pair.of(23, Slot.of(item)),
                Pair.of(24, Byte.of((byte) displayType.ordinal()))
        );
    }
}
