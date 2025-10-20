package me.combimagnetron.passport.internal.entity.impl.passive.horse;

public record Variant(Color color, Marking marking) {

    public static Variant of(Color color, Marking marking) {
        return new Variant(color, marking);
    }

    public static Variant fromId(int id) {
        return new Variant(Color.values()[id & 0xFF], Marking.values()[id >> 8]);
    }

    public int id() {
        return (marking.ordinal() << 8) + color.ordinal();
    }

}