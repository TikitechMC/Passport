package me.combimagnetron.passport.util.math;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public record Vec2f(float x, float y) {

    @Contract(pure = true)
    public int xi() {
        return (int) x;
    }

    @Contract(pure = true)
    public int yi() {
        return (int) y;
    }

    @Contract(value = "_, _ -> new", pure = true)
    @NotNull
    public static Vec2f of(float x, float y) {
        return new Vec2f(x, y);
    }

    @Contract(value = "_ -> new", pure = true)
    @NotNull
    public Vec2f mul(float factor) {
        return new Vec2f(this.x * factor, this.y * factor);
    }

    @Contract(value = "_, _ -> new", pure = true)
    @NotNull
    public Vec2f mul(float x, float y) {
        return new Vec2f(this.x * x, this.y * y);
    }

    @Contract(value = "_ -> new", pure = true)
    @NotNull
    public Vec2f mul(@NotNull Vec2f vec2d) {
        return mul(vec2d.x, vec2d.y);
    }

    @Contract(value = "_ -> new", pure = true)
    @NotNull
    public Vec2f div(float factor) {
        return new Vec2f(this.x / factor, this.y / factor);
    }

    @Contract(value = "_, _ -> new", pure = true)
    @NotNull
    public Vec2f div(float x, float y) {
        return new Vec2f(this.x / x, this.y / y);
    }

    @Contract(value = "_ -> new", pure = true)
    @NotNull
    public Vec2f div(@NotNull Vec2f vec2d) {
        return div(vec2d.x, vec2d.y);
    }

    @Contract(value = "_ -> new", pure = true)
    @NotNull
    public Vec2f add(float factor) {
        return new Vec2f(this.x + factor, this.y + factor);
    }

    @Contract(value = "_, _ -> new", pure = true)
    @NotNull
    public Vec2f add(float x, float y) {
        return new Vec2f(this.x + x, this.y + y);
    }

    @Contract(value = "_ -> new", pure = true)
    @NotNull
    public Vec2f add(@NotNull Vec2f vec2d) {
        return add(vec2d.x, vec2d.y);
    }

    @Contract(value = "_ -> new", pure = true)
    @NotNull
    public Vec2f sub(float factor) {
        return new Vec2f(this.x - factor, this.y - factor);
    }

    @Contract(value = "_, _ -> new", pure = true)
    @NotNull
    public Vec2f sub(float x, float y) {
        return new Vec2f(this.x - x, this.y - y);
    }

    @Contract(value = "_ -> new", pure = true)
    @NotNull
    public Vec2f sub(@NotNull Vec2f vec2d) {
        return sub(vec2d.x, vec2d.y);
    }

}
