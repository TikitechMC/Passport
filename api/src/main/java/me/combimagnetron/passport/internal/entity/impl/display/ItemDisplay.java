package me.combimagnetron.passport.internal.entity.impl.display;

import me.combimagnetron.passport.internal.entity.metadata.type.Vector3d;

import java.util.function.Consumer;

public class ItemDisplay extends Display {
    public ItemDisplay(Vector3d position, Consumer<Display> loaded) {
        super(position, loaded);
    }



}
