package me.combimagnetron.passport.internal.entity.metadata.type;

import me.combimagnetron.passport.internal.network.ByteBuffer;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;

public record Chat(@NotNull Component component) implements MetadataType<Component> {

    public static Chat of(Component component) {
        return new Chat(component);
    }

    @Override
    public byte[] bytes() {
        final ByteBuffer buffer = ByteBuffer.empty();
        //buffer.write(ByteBuffer.Adapter.STRING, GsonComponentSerializer.gson().serialize(component));
        return buffer.bytes();
    }

    @Override
    public Component object() {
        return component;
    }
}
