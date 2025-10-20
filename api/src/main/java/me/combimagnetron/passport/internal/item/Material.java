package me.combimagnetron.passport.internal.item;

import me.combimagnetron.passport.data.Identifier;

public interface Material {
    Material AIR = new Material() {
        @Override
        public int material() {
            return 0;
        }

        @Override
        public Identifier identifier() {
            return Identifier.of("minecraft", "air");
        }
    };

    int material();

    Identifier identifier();

}
