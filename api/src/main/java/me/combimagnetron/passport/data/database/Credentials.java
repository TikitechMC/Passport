package me.combimagnetron.passport.data.database;

import me.combimagnetron.passport.config.annotation.Config;

public interface Credentials {

    static Credentials redis(String username, String password, int port, String host) {
        return new RedisCredentials(username, password, port, host);
    }

    @Config
    record RedisCredentials(String username, String password, int port, String host) implements Credentials {

    }


}
