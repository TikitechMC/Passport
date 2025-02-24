package me.combimagnetron.passport.user;

import me.combimagnetron.passport.internal.entity.Entity;
import me.combimagnetron.passport.internal.entity.metadata.type.Vector3d;
import me.combimagnetron.passport.internal.network.ByteBuffer;
import me.combimagnetron.passport.internal.network.Connection;
import me.combimagnetron.passport.util.Pos2D;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;

import java.util.UUID;

public interface User<T extends Audience> {

     T platformSpecificPlayer();

     String name();

     default void message(Component component) {
          platformSpecificPlayer().sendMessage(component);
     }

     UUID uniqueIdentifier();

     Connection connection();

     Vector3d position();

     void show(Entity entity);

     int entityId();

     Vector3d rotation();

     /*
     1. UUID -> Unique Identifier
     2. String -> Name
     3. 3x Double -> Vector3d Position
      */
     default ByteBuffer serialize() {
          ByteBuffer buffer = ByteBuffer.empty();
          buffer.write(ByteBuffer.Adapter.UUID, uniqueIdentifier());
          buffer.write(ByteBuffer.Adapter.STRING, name());
          buffer.write(ByteBuffer.Adapter.DOUBLE, position().x()).write(ByteBuffer.Adapter.DOUBLE, position().y()).write(ByteBuffer.Adapter.DOUBLE, position().z());
          return buffer;
     }

}
