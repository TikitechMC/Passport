package me.combimagnetron.passport.internal.entity.metadata.type;

import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.UUID;

public record OptLivingEntityRef(@Nullable UUID uuid) implements MetadataType<Optional<UUID>> {
    @Override
    public byte[] bytes() {
        return new byte[0];
    }

    public static OptLivingEntityRef of(@Nullable UUID uuid) {
        return new OptLivingEntityRef(uuid);
    }

    @Override
    public Optional<UUID> object() {
        return Optional.ofNullable(uuid);
    }
}
