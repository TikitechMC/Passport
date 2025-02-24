package me.combimagnetron.passport.user;

import me.combimagnetron.passport.internal.network.ByteBuffer;
import net.kyori.adventure.audience.Audience;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

public interface UserHandler<V extends Audience, U extends User<V>> {

    U user(V t);

    Optional<U> user(UUID uuid);

    Optional<U> user(String name);

    Collection<U> users();

    Collection<U> global();

    U deserialize(ByteBuffer buffer);

}
