package me.combimagnetron.passport.internal.component;

import me.combimagnetron.passport.internal.network.ByteBuffer;
import net.kyori.adventure.nbt.BinaryTag;
import net.kyori.adventure.nbt.BinaryTagType;

public interface DynamicDataComponent {

    <T extends BinaryTag> BinaryTagType<T> nbtType();

    <T> ByteBuffer.Adapter<T> adapter();

    BinaryTag nbt();

    ByteBuffer buffer();

}
