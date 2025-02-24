package me.combimagnetron.passport.internal.entity.metadata.type;

import com.github.retrooper.packetevents.protocol.item.ItemStack;
import com.github.retrooper.packetevents.protocol.item.type.ItemTypes;
import com.github.retrooper.packetevents.protocol.player.ClientVersion;
import me.combimagnetron.passport.internal.network.ByteBuffer;
import me.combimagnetron.passport.internal.item.Item;
import org.checkerframework.checker.nullness.qual.NonNull;

public record Slot(@NonNull Item item) implements MetadataType<ItemStack> {

    public static Slot of(Item item) {
        return new Slot(item);
    }

    @Override
    public byte[] bytes() {
        final ByteBuffer buffer = ByteBuffer.empty();
        buffer.write(ByteBuffer.Adapter.ITEM, item);
        return buffer.bytes();
    }

    @Override
    public ItemStack object() {
        return new ItemStack.Builder().type(ItemTypes.getById(ClientVersion.V_1_21_4, item.material().material())).amount(item.amount()).build();
    }
}
