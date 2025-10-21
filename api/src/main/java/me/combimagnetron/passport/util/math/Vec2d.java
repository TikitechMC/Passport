package me.combimagnetron.passport.util.math;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public record Vec2d(double x, double y) {

    @Contract(pure = true)
    public int xi() {
        return Math.round((float)x);
    }

    @Contract(pure = true)
    public int yi() {
        return Math.round((float) y);
    }

    @Contract(value = "_, _ -> new", pure = true)
    @NotNull
    public static Vec2d of(double x, double y) {
        return new Vec2d(x, y);
    }

    @Contract(value = "_ -> new", pure = true)
    @NotNull
    public Vec2d mul(double factor) {
        return new Vec2d(this.x * factor, this.y * factor);
    }

    @Contract(value = "_, _ -> new", pure = true)
    @NotNull
    public Vec2d mul(double x, double y) {
        return new Vec2d(this.x * x, this.y * y);
    }

    @Contract(value = "_ -> new", pure = true)
    @NotNull
    public Vec2d mul(@NotNull Vec2d vec2d) {
        return mul(vec2d.x, vec2d.y);
    }

    @Contract(value = "_ -> new", pure = true)
    @NotNull
    public Vec2d div(double factor) {
        return new Vec2d(this.x / factor, this.y / factor);
    }

    @Contract(value = "_, _ -> new", pure = true)
    @NotNull
    public Vec2d div(double x, double y) {
        return new Vec2d(this.x / x, this.y / y);
    }

    @Contract(value = "_ -> new", pure = true)
    @NotNull
    public Vec2d div(@NotNull Vec2d vec2d) {
        return div(vec2d.x, vec2d.y);
    }

    @Contract(value = "_ -> new", pure = true)
    @NotNull
    public Vec2d add(double factor) {
        return new Vec2d(this.x + factor, this.y + factor);
    }

    @Contract(value = "_, _ -> new", pure = true)
    @NotNull
    public Vec2d add(double x, double y) {
        return new Vec2d(this.x + x, this.y + y);
    }

    @Contract(value = "_ -> new", pure = true)
    @NotNull
    public Vec2d add(@NotNull Vec2d vec2d) {
        return add(vec2d.x, vec2d.y);
    }

    @Contract(value = "_ -> new", pure = true)
    @NotNull
    public Vec2d sub(double factor) {
        return new Vec2d(this.x - factor, this.y - factor);
    }

    @Contract(value = "_, _ -> new", pure = true)
    @NotNull
    public Vec2d sub(double x, double y) {
        return new Vec2d(this.x - x, this.y - y);
    }

    @Contract(value = "_ -> new", pure = true)
    @NotNull
    public Vec2d sub(@NotNull Vec2d vec2d) {
        return sub(vec2d.x, vec2d.y);
    }
}
