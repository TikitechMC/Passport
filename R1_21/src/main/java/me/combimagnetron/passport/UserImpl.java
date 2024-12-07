package me.combimagnetron.passport;

import me.combimagnetron.passport.internal.entity.metadata.type.Vector3d;
import me.combimagnetron.passport.internal.network.Connection;
import me.combimagnetron.passport.user.User;
import org.bukkit.entity.Player;

import java.util.UUID;

public class UserImpl implements User<Player> {
    private final ConnectionImpl connection = new ConnectionImpl(this);
    private final Player player;

    public UserImpl(Player player) {
        this.player = player;
    }

    @Override
    public Player platformSpecificPlayer() {
        return player;
    }

    @Override
    public String name() {
        return player.getName();
    }

    @Override
    public UUID uniqueIdentifier() {
        return player.getUniqueId();
    }

    @Override
    public Connection connection() {
        return connection;
    }

    @Override
    public Vector3d position() {
        return Vector3d.vec3(player.getLocation().getX(), player.getLocation().getY(), player.getLocation().getZ());
    }
}
