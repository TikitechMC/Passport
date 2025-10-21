package me.combimagnetron.passport.util.math;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public record Vec4i(int first, int second, int third, int fourth) {
    private static final Vec4i ZERO = Vec4i.of(0, 0, 0, 0);

    @Contract(pure = true)
    @NotNull
    public static Vec4i zero() {
        return ZERO;
    }

    @Contract(value = "_, _, _, _ -> new", pure = true)
    @NotNull
    public static Vec4i of(int first, int second, int third, int fourth) {
        return new Vec4i(first, second, third, fourth);
    }

    @Contract(value = "_ -> new", pure = true)
    @NotNull
    public Vec4i mul(int factor) {
        return new Vec4i(this.first * factor, this.second * factor, this.third * factor, this.fourth * factor);
    }

    @Contract(value = "_, _, _, _ -> new", pure = true)
    @NotNull
    public Vec4i mul(int first, int second, int third, int fourth) {
        return new Vec4i(this.first * first, this.second * second, this.third * third, this.fourth * fourth);
    }

    @Contract(value = "_ -> new", pure = true)
    @NotNull
    public Vec4i mul(@NotNull Vec4i vec4i) {
        return mul(vec4i.first, vec4i.second, vec4i.third, vec4i.fourth);
    }

    @Contract(value = "_ -> new", pure = true)
    @NotNull
    public Vec4i div(int factor) {
        return new Vec4i(this.first / factor, this.second / factor, this.third / factor, this.fourth / factor);
    }

    @Contract(value = "_, _, _, _ -> new", pure = true)
    @NotNull
    public Vec4i div(int first, int second, int third, int fourth) {
        return new Vec4i(this.first / first, this.second / second, this.third / third, this.fourth / fourth);
    }

    @Contract(value = "_ -> new", pure = true)
    @NotNull
    public Vec4i div(@NotNull Vec4i vec4i) {
        return div(vec4i.first, vec4i.second, vec4i.third, vec4i.fourth);
    }

    @Contract(value = "_ -> new", pure = true)
    @NotNull
    public Vec4i add(int factor) {
        return new Vec4i(this.first + factor, this.second + factor, this.third + factor, this.fourth + factor);
    }

    @Contract(value = "_, _, _, _ -> new", pure = true)
    @NotNull
    public Vec4i add(int first, int second, int third, int fourth) {
        return new Vec4i(this.first + first, this.second + second, this.third + third, this.fourth + fourth);
    }

    @Contract(value = "_ -> new", pure = true)
    @NotNull
    public Vec4i add(@NotNull Vec4i vec4i) {
        return add(vec4i.first, vec4i.second, vec4i.third, vec4i.fourth);
    }

    @Contract(value = "_ -> new", pure = true)
    @NotNull
    public Vec4i sub(int factor) {
        return new Vec4i(this.first - factor, this.second - factor, this.third - factor, this.fourth - factor);
    }

    @Contract(value = "_, _, _, _ -> new", pure = true)
    @NotNull
    public Vec4i sub(int first, int second, int third, int fourth) {
        return new Vec4i(this.first - first, this.second - second, this.third - third, this.fourth - fourth);
    }

    @Contract(value = "_ -> new", pure = true)
    @NotNull
    public Vec4i sub(@NotNull Vec4i vec4i) {
        return sub(vec4i.first, vec4i.second, vec4i.third, vec4i.fourth);
    }

}
