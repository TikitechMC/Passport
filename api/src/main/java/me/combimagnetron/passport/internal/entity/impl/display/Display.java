package me.combimagnetron.passport.internal.entity.impl.display;

import me.combimagnetron.passport.internal.entity.Entity;
import me.combimagnetron.passport.internal.entity.metadata.Metadata;
import me.combimagnetron.passport.internal.entity.metadata.type.Float;
import me.combimagnetron.passport.internal.entity.metadata.type.Quaternion;
import me.combimagnetron.passport.internal.entity.metadata.type.VarInt;
import me.combimagnetron.passport.internal.entity.metadata.type.Vector3d;
import me.combimagnetron.passport.internal.entity.metadata.type.Byte;
import me.combimagnetron.passport.util.Pair;

import java.util.function.Consumer;

@SuppressWarnings("unused")
public class Display extends Entity.AbstractEntity {
    protected int interpolationDelay = 0;
    protected int interpolationDuration = 0;
    protected Transformation transformation = new Transformation(Vector3d.vec3(0, 0, 0), Vector3d.vec3(1, 1, 1), Quaternion.of(0, 0, 0, 1), Quaternion.of(0, 0, 0, 1));
    protected Billboard billboard = Billboard.FIXED;
    protected int brightness = -1;
    protected float viewRange = 1;
    protected Shadow shadow = Shadow.shadow();
    protected int glowOverride = -1;
    protected float width = 0;
    protected float height = 0;

    public Display(Vector3d position, Consumer<Display> loaded) {
        super(position);
        loaded.accept(this);
    }

    @Override
    public Metadata extend() {
        return null;
    }

    @Override
    public Data data() {
        return Data.of(0);
    }

    @Override
    public Type type() {
        return null;
    }

    public record Transformation(Vector3d translation, Vector3d scale, Quaternion rotationLeft, Quaternion rotationRight) {

        public static Transformation transformation() {
            return of(Vector3d.vec3(0, 0, 0), Vector3d.vec3(1, 1, 1), Quaternion.of(0, 0, 0, 1), Quaternion.of(0, 0, 0, 1));
        }

        public static Transformation of(Vector3d translation, Vector3d scale, Quaternion rotationLeft, Quaternion rotationRight) {
            return new Transformation(translation, scale, rotationLeft, rotationRight);
        }

        public Transformation translation(Vector3d translation) {
            return of(translation, scale(), rotationLeft(), rotationRight());
        }

        public Transformation scale(Vector3d scale) {
            return of(translation(), scale, rotationLeft(), rotationRight());
        }

        public Transformation rotationLeft(Quaternion rotationLeft) {
            return of(translation(), scale(), rotationLeft, rotationRight());
        }

        public Transformation rotationRight(Quaternion rotationRight) {
            return of(translation(), scale(), rotationLeft(), rotationRight);
        }

    }

    public record Shadow(float radius, float strength) {

        public static Shadow of(float radius, float strength) {
            return new Shadow(radius, strength);
        }

        public static Shadow shadow() {
            return of(0, 1);
        }

    }

    public Metadata base() {
        return Metadata.of(
                Pair.of(8, VarInt.of(interpolationDelay())),
                Pair.of(9, VarInt.of(0)),
                Pair.of(10, VarInt.of(0)),
                Pair.of(11, transformation().translation()),
                Pair.of(12, transformation().scale()),
                Pair.of(13, transformation().rotationLeft()),
                Pair.of(14, transformation().rotationRight()),
                Pair.of(15, Byte.of(billboard().constraint())),
                Pair.of(16, VarInt.of(brightness())),
                Pair.of(17, Float.of(viewRange())),
                Pair.of(18, Float.of(shadow().radius())),
                Pair.of(19, Float.of(shadow().strength())),
                Pair.of(20, Float.of(width())),
                Pair.of(21, Float.of(height())),
                Pair.of(22, VarInt.of(glowOverride()))
        );
    }

    public interface Billboard {
        Billboard FIXED = of(0);
        Billboard VERTICAL = of(1);
        Billboard HORIZONTAL = of(2);
        Billboard CENTER = of(3);

        byte constraint();

        static Billboard of(int constraint) {
            return new Impl((byte)constraint);
        }

        record Impl(byte constraint) implements Billboard {

        }

    }

    public int interpolationDelay() {
        return interpolationDelay;
    }

    public void interpolationDelay(int interpolationDelay) {
        this.interpolationDelay = interpolationDelay;
    }

    public int interpolationDuration() {
        return interpolationDuration;
    }

    public void interpolationDuration(int interpolationDuration) {
        this.interpolationDuration = interpolationDuration;
    }

    public Transformation transformation() {
        return transformation;
    }

    public void transformation(Transformation transformation) {
        this.transformation = transformation;
    }

    public Billboard billboard() {
        return billboard;
    }

    public void billboard(Billboard billboard) {
        this.billboard = billboard;
    }

    public int brightness() {
        return brightness;
    }

    public void brightness(int brightness) {
        this.brightness = brightness;
    }

    public float viewRange() {
        return viewRange;
    }

    public void viewRange(float viewRange) {
        this.viewRange = viewRange;
    }

    public Shadow shadow() {
        return shadow;
    }

    public void shadow(Shadow shadow) {
        this.shadow = shadow;
    }

    public float width() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float height() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public int glowOverride() {
        return glowOverride;
    }

    public void glowOverride(int glowOverride) {
        this.glowOverride = glowOverride;
    }

}
