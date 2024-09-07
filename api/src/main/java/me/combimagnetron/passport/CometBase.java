package me.combimagnetron.passport;

import me.combimagnetron.passport.internal.network.Network;
import me.combimagnetron.passport.user.UserHandler;
import net.kyori.adventure.audience.Audience;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Path;
import java.util.UUID;

public interface CometBase<T> {

    static <T> CometBase<T> comet() {
        return (CometBase<T>) Holder.INSTANCE;
    }

    Network network();

    UserHandler<? extends Audience> users();

    Path dataFolder();

    default Logger logger() {
        return LoggerFactory.getLogger(CometBase.class);
    }

    T plugin();

    final class Holder {
        public static CometBase<?> INSTANCE = null;

    }

}
