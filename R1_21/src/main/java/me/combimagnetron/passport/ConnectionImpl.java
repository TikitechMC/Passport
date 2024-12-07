package me.combimagnetron.passport;

import me.combimagnetron.passport.internal.network.Connection;
import me.combimagnetron.passport.internal.network.packet.Packet;
import me.combimagnetron.passport.user.User;
import org.bukkit.craftbukkit.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class ConnectionImpl implements Connection {
    private final User<Player> user;

    public ConnectionImpl(User<Player> user) {
        this.user = user;
    }

    @Override
    public void send(Packet packetHolder) {
        ((CraftPlayer) user.platformSpecificPlayer()).getHandle().connection.connection.channel.pipeline().write(packetHolder.write());
    }
}
