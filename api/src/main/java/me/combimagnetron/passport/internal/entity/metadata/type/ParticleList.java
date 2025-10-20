package me.combimagnetron.passport.internal.entity.metadata.type;

import me.combimagnetron.passport.internal.particle.Particle;

import java.util.List;

public record ParticleList(List<Particle> particleList) implements MetadataType<List<Particle>> {
    @Override
    public byte[] bytes() {
        return new byte[0];
    }

    public static ParticleList of(List<Particle> particleList) {
        return new ParticleList(particleList);
    }

    @Override
    public List<Particle> object() {
        return particleList;
    }
}
