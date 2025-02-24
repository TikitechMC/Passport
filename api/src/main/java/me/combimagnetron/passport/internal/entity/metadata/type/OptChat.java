package me.combimagnetron.passport.internal.entity.metadata.type;

import me.combimagnetron.passport.internal.network.ByteBuffer;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer;
import org.checkerframework.checker.nullness.qual.Nullable;

public record OptChat(@Nullable Component component) implements MetadataType<Component> {

    public static OptChat of(@Nullable Component component) {
        return new OptChat(component);
    }

    @Override
    public byte[] bytes() {
        final ByteBuffer buffer = ByteBuffer.empty();
        final boolean present = component != null;
        buffer.write(ByteBuffer.Adapter.BOOLEAN, present);
        if (present) {
            buffer.write(ByteBuffer.Adapter.STRING ,GsonComponentSerializer.gson().serialize(component));
        }
        return buffer.bytes();
    }

    @Override
    public @Nullable Component object() {
        return component;
    }
}
