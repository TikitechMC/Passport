package me.combimagnetron.passport.internal.entity.metadata.type;

import me.combimagnetron.passport.internal.network.ByteBuffer;
import me.combimagnetron.passport.internal.item.Item;
import org.checkerframework.checker.nullness.qual.NonNull;

public record Slot(@NonNull Item item) implements MetadataType {

    public static Slot of(Item item) {
        return new Slot(item);
    }

    @Override
    public byte[] bytes() {
        final ByteBuffer buffer = ByteBuffer.empty();
        buffer.write(ByteBuffer.Adapter.ITEM, item);
        return buffer.bytes();
    }
}
