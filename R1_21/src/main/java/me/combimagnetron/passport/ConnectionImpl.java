package me.combimagnetron.passport;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.ByteToMessageDecoder;
import me.combimagnetron.passport.internal.network.ByteBuffer;
import me.combimagnetron.passport.internal.network.packet.ClientPacket;
import me.combimagnetron.passport.internal.network.packet.Packet;
import me.combimagnetron.passport.user.User;
import net.minecraft.network.Connection;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.entity.CraftEntity;
import org.bukkit.craftbukkit.entity.CraftPlayer;
import org.bukkit.craftbukkit.util.CraftMagicNumbers;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.zip.Deflater;

public class ConnectionImpl //implements //me.combimagnetron.passport.internal.network.Connection<Packet> {
{//private final static VersionRegistry REGISTRY = new VersionRegistryImpl();
    private final Player player;
    private final User<Player> user;
    private final Passport<JavaPlugin> library;
    private ChannelPipeline channelPipeline;

    public static ConnectionImpl of(User<Player> user, Passport<JavaPlugin> library) {
        return new ConnectionImpl(user, library);
    }

    protected ConnectionImpl(User<Player> user, Passport<JavaPlugin> library) {
        this.player = user.platformSpecificPlayer();
        this.library = library;
        this.user = user;
        inject();
    }

    private void inject() {
        ServerGamePacketListenerImpl serverGamePacketListener = ((CraftPlayer) player).getHandle().connection;
        Connection connection = serverGamePacketListener.connection;
        this.channelPipeline = connection.channel.pipeline().addLast(new ChannelInjector(library, user));
    }

    //@Override
    public void send(Packet packetContainer) {
        if (packetContainer == null) return;
        if (!(packetContainer instanceof ClientPacket clientPacket)) {
            return;
        }
        ByteBuffer buffer = ByteBuffer.empty();
        Bukkit.getLogger().info(" before " + packetContainer.getClass().getName());
        //buffer.write(ByteBuffer.Adapter.VAR_INT, VersionRegistry.client(clientPacket.getClass()));
        buffer.write(ByteBuffer.Adapter.VAR_INT, packetContainer.write().length);
        buffer.write(packetContainer.write());
        byte[] bytes = buffer.bytes();
        Deflater deflater = new Deflater();
        deflater.setInput(bytes);
        deflater.finish();
        byte[] output = new byte[bytes.length];
        deflater.deflate(output);
        deflater.end();
        channelPipeline.write(Unpooled.wrappedBuffer(output));
    }

    protected static class ChannelInjector extends ChannelDuplexHandler {
        private final Passport<JavaPlugin> library;
        private final User<Player> user;

        protected ChannelInjector(Passport<JavaPlugin> library, User<Player> user) {
            this.library = library;
            this.user = user;
        }

        @Override
        public void channelRead(ChannelHandlerContext ctx, @NotNull Object message) throws Exception {
            super.channelRead(ctx, message);
        }

    }

    private static class Decoder extends ByteToMessageDecoder {

        @Override
        protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
            final byte[] bytes = in.array();
            final ByteBuffer byteBuffer = ByteBuffer.of(bytes);
            /*final Class<? extends ServerPacket> clazz = VersionRegistry.server(byteBuffer.read(ByteBuffer.Adapter.VAR_INT));
            ServerPacket packet = (ServerPacket) clazz.getDeclaredMethod("from", ByteBuffer.class).invoke(null, byteBuffer);
            out.add(packet);*/
        }
    }

}