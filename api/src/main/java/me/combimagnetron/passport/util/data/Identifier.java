package me.combimagnetron.passport.util.data;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public record Identifier(Namespace namespace, Key key) implements Identifiable {
    public static final Namespace DEFAULT_NAMESPACE = Namespace.of("sunscreen");

    @Contract("_ -> new")
    public static @NotNull Identifier of(@NotNull Key key) {
        return new Identifier(DEFAULT_NAMESPACE, key);
    }

    @Contract("_ -> new")
    public static @NotNull Identifier of(@NotNull String key) {
        return new Identifier(DEFAULT_NAMESPACE, Key.of(key));
    }

    @Contract("_, _ -> new")
    public static @NotNull Identifier of(@NotNull Namespace namespace, @NotNull Key key) {
        return new Identifier(namespace, key);
    }

    @Contract("_, _ -> new")
    public static @NotNull Identifier of(@NotNull String namespace, @NotNull String key) {
        return new Identifier(Namespace.of(namespace), Key.of(key));
    }

    @Contract("_ -> new")
    public static @NotNull Identifier split(@NotNull String string) {
        String[] split = string.split(":");
        return new Identifier(Namespace.of(split[0]), Key.of(split[1]));
    }

    public @NotNull String string() {
        return namespace.string() + ":" + key.string();
    }

    @Override
    public @NotNull String toString() {
        return string();
    }

    public record Namespace(@NotNull String string) implements Identifiable {
        public static Namespace of(@NotNull String string) {
            return new Namespace(string);
        }
    }

    public record Key(@NotNull String string) implements Identifiable {
        public static Key of(@NotNull String string) {
            return new Key(string);
        }
    }

    public enum TestDepth {
        WHOLE, NAMESPACE, KEY, ANY
    }

    public boolean anyMatch(@NotNull Identifiable identifiable, @NotNull TestDepth testDepth) {
        return switch (testDepth) {
            case WHOLE -> this == identifiable;
            case KEY -> this.key == identifiable;
            case NAMESPACE -> this.namespace == identifiable;
            default -> this == identifiable || this.namespace == identifiable || this.key == identifiable;
        };
    }

}
