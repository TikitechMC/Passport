package me.combimagnetron.passport.logic.state.origin;


import me.combimagnetron.passport.util.data.Identifier;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class Origin {
    private final UUID uuid = UUID.randomUUID();
    private final Identifier identifier;

    protected Origin(Identifier identifier) {
        this.identifier = identifier;
    }

    public static Origin local(@NotNull Class<?> clazz) {
        return new LocalOrigin(Identifier.of(Thread.currentThread().getName(), clazz.getSimpleName()), clazz);
    }

    public @NotNull UUID uuid() {
        return uuid;
    }

    public @NotNull Identifier identifier() {
        return identifier;
    }

    static final class LocalOrigin extends Origin {
        private final Class<?> clazz;

        private LocalOrigin(Identifier identifier, Class<?> clazz) {
            super(identifier);
            this.clazz = clazz;
        }

        public @NotNull Class<?> clazz() {
            return clazz;
        }

    }
}