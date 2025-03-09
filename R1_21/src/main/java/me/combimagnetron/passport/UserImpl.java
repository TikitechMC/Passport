package me.combimagnetron.passport;

import me.combimagnetron.passport.internal.entity.Entity;
import me.combimagnetron.passport.internal.entity.metadata.type.Vector3d;
import me.combimagnetron.passport.internal.network.Connection;
import me.combimagnetron.passport.user.User;
import me.combimagnetron.passport.util.Pos2D;
import org.bukkit.entity.Player;

import java.util.Optional;
import java.util.UUID;

public class UserImpl implements User<Player> {
    private final Connection connection;
    private final Player player;
    private float fov = 70;

    public UserImpl(Player player) {
        this.player = player;
        this.connection = new PacketEventsConnectionImpl<Player>(this.player);
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

    @Override
    public void show(Entity entity) {
        /*WrapperPlayServerSpawnEntity clientSpawnEntity = new WrapperPlayServerSpawnEntity(entity.id().intValue(), Optional.of(entity.uuid()), EntityTypes.getById(ClientVersion.V_1_21_4, entity.type().id()), new com.github.retrooper.packetevents.util.Vector3d(entity.position().x(), entity.position().y(), entity.position().z()), (float) entity.rotation().x(), (float) entity.rotation().y(), (float) entity.rotation().z(), entity.data().i(), Optional.empty());
        WrapperPlayServerEntityMetadata clientEntityMetadata = new WrapperPlayServerEntityMetadata(entity.id().intValue(), entity.type().metadata().entityData());
        connection().send(clientSpawnEntity);
        connection().send(clientEntityMetadata);*/
    }

    @Override
    public int entityId() {
        return player.getEntityId();
    }

    @Override
    public Vector3d rotation() {
        return Vector3d.vec3(player.getLocation().getYaw(), player.getLocation().getPitch(), 0);
    }

    @Override
    public int gameMode() {
        return player.getGameMode().ordinal();
    }
}
