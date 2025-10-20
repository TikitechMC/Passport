package me.combimagnetron.passport.internal.particle;

import me.combimagnetron.passport.data.Identifier;

public interface Particle {

    int id();

    Identifier identifier();

    record Simple(int id, Identifier identifier) implements Particle {

    }

    static Particle simple(int id, Identifier identifier) {
        return new Simple(id, identifier);
    }

}
