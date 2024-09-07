package me.combimagnetron.passport.user;

import me.combimagnetron.passport.internal.network.ByteBuffer;
import net.kyori.adventure.audience.Audience;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

public interface UserHandler<T extends Audience> {

    User<T> user(T t);

    Optional<User<T>> user(UUID uuid);

    Optional<User<T>> user(String name);

    Collection<User<T>> users();

    Collection<User<T>> global();

    User<T> deserialize(ByteBuffer buffer);

}
