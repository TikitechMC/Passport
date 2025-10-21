package me.combimagnetron.passport.util.math;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public record Vec2i(int x, int y) {
    private static final Vec2i ZERO = Vec2i.of(0, 0);

    @Contract(pure = true)
    @NotNull
    public static Vec2i zero() {
        return ZERO;
    }

    @Contract(value = "_, _ -> new", pure = true)
    @NotNull
    public static Vec2i of(int x, int y) {
        return new Vec2i(x, y);
    }

    @Contract(value = "_ -> new", pure = true)
    @NotNull
    public Vec2i mul(int factor) {
        return new Vec2i(this.x * factor, this.y * factor);
    }

    @Contract(value = "_, _ -> new", pure = true)
    @NotNull
    public Vec2i mul(int x, int y) {
        return new Vec2i(this.x * x, this.y * y);
    }

    @Contract(value = "_ -> new", pure = true)
    @NotNull
    public Vec2i mul(@NotNull Vec2i vec2d) {
        return mul(vec2d.x, vec2d.y);
    }

    @Contract(value = "_ -> new", pure = true)
    @NotNull
    public Vec2i div(int factor) {
        return new Vec2i(this.x / factor, this.y / factor);
    }

    @Contract(value = "_, _ -> new", pure = true)
    @NotNull
    public Vec2i div(int x, int y) {
        return new Vec2i(this.x / x, this.y / y);
    }

    @Contract(value = "_ -> new", pure = true)
    @NotNull
    public Vec2i div(@NotNull Vec2i vec2d) {
        return div(vec2d.x, vec2d.y);
    }

    @Contract(value = "_ -> new", pure = true)
    @NotNull
    public Vec2i add(int factor) {
        return new Vec2i(this.x + factor, this.y + factor);
    }

    @Contract(value = "_, _ -> new", pure = true)
    @NotNull
    public Vec2i add(int x, int y) {
        return new Vec2i(this.x + x, this.y + y);
    }

    @Contract(value = "_ -> new", pure = true)
    @NotNull
    public Vec2i add(@NotNull Vec2i vec2d) {
        return add(vec2d.x, vec2d.y);
    }

    @Contract(value = "_ -> new", pure = true)
    @NotNull
    public Vec2i sub(int factor) {
        return new Vec2i(this.x - factor, this.y - factor);
    }

    @Contract(value = "_, _ -> new", pure = true)
    @NotNull
    public Vec2i sub(int x, int y) {
        return new Vec2i(this.x - x, this.y - y);
    }

    @Contract(value = "_ -> new", pure = true)
    @NotNull
    public Vec2i sub(@NotNull Vec2i vec2d) {
        return sub(vec2d.x, vec2d.y);
    }

}
