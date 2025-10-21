package me.combimagnetron.passport;

import com.github.retrooper.packetevents.PacketEventsAPI;
import me.combimagnetron.passport.user.User;
import me.combimagnetron.passport.user.UserHandler;
import me.combimagnetron.passport.util.placeholder.PlaceholderRegistry;
import net.kyori.adventure.audience.Audience;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Path;

public interface Passport<T> {

    static <T> Passport<T> passport() {
        return (Passport<T>) Holder.INSTANCE;
    }

    UserHandler<? extends Audience, ? extends User<? extends Audience>> users();

    Path dataFolder();

    PlaceholderRegistry placeholders();

    default Logger logger() {
        return LoggerFactory.getLogger(Passport.class);
    }

    PacketEventsAPI<?> packetEventsApi();

    T plugin();

    final class Holder {
        public static Passport<?> INSTANCE = null;

    }

}
