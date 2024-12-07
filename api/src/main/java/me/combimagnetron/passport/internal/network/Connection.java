package me.combimagnetron.passport.internal.network;

//import me.combimagnetron.generated.R1_21.packet.play.ClientSpawnEntity;
import me.combimagnetron.passport.internal.entity.Entity;
import me.combimagnetron.passport.internal.network.packet.Packet;

public interface Connection {

    void send(Packet packetHolder);

    default void spawn(Entity entity) {
        //send(ClientSpawnEntity.SpawnEntity(entity.id().intValue(), entity.uuid(), entity.type().id(), entity.position().x(), entity.position().y(), entity.position().z(), (byte)(entity.rotation().x() * 256 / 360), (byte)(entity.rotation().y() * 256 / 360), (byte)(entity.rotation().z() * 256 / 360), entity.data().i(), (short)entity.velocity().x()));
    }

}