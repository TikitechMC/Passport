package me.combimagnetron.passport.internal.entity.metadata.type;

import me.combimagnetron.passport.internal.network.ByteBuffer;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer;
import org.checkerframework.checker.nullness.qual.NonNull;

public record Chat(@NonNull Component component) implements MetadataType {

    public static Chat of(Component component) {
        return new Chat(component);
    }

    @Override
    public byte[] bytes() {
        final ByteBuffer buffer = ByteBuffer.empty();
        buffer.write(ByteBuffer.Adapter.STRING, GsonComponentSerializer.gson().serialize(component));
        return buffer.bytes();
    }
}
