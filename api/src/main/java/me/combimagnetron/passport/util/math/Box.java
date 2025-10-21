package me.combimagnetron.passport.util.math;

public final class Box {
    private Vec2i pos;
    private Vec2i size;

    Box(Vec2i pos, Vec2i size) {
        this.pos = pos;
        this.size = size;
    }

    public static Box of(Vec2i pos, Vec2i size) {
        return new Box(pos, size);
    }

    public void extend(Vec2i updated) {
        int updatedX = updated.x();
        int updatedY = updated.y();
        this.pos = Vec2i.of(Math.min(this.pos.x(), updatedX), Math.min(this.pos.y(), updatedY));
        this.size = Vec2i.of(Math.max(this.size.x(), updatedX - this.pos.x() + 1), Math.max(this.size.y(), updatedY - this.pos.y() + 1));
    }

    public boolean inBox(Vec2i pos) {
        return pos.x() >= this.pos.x() && pos.x() <= (this.pos.x() + this.size.x() - 1) && pos.y() >= this.pos.y() && pos.y() <= (this.pos.y() + this.size.y() - 1);
    }

    public Vec2i pos() {
        return pos;
    }

    public Vec2i size() {
        return size;
    }

}