package me.combimagnetron.passport.internal.menu;

import com.github.retrooper.packetevents.PacketEvents;
import com.github.retrooper.packetevents.PacketEventsAPI;
import com.github.retrooper.packetevents.protocol.item.ItemStack;
import com.github.retrooper.packetevents.protocol.item.type.ItemTypes;
import com.github.retrooper.packetevents.protocol.player.ClientVersion;
import com.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientClickWindow;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerCloseWindow;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerOpenWindow;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerWindowItems;
import me.combimagnetron.passport.util.Pos2D;
import me.combimagnetron.passport.internal.network.Connection;
import me.combimagnetron.passport.internal.item.Item;
import me.combimagnetron.passport.user.User;
import me.combimagnetron.passport.util.Pair;
import net.kyori.adventure.text.Component;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.stream.Stream;

public interface ChestMenu extends ContainerMenu {

    void click(Pos2D click);

    static ChestMenu menu(User<?> viewer) {
        return new Impl(viewer);
    }

    final class Impl implements ChestMenu {
        private final Contents contents = new Contents(c -> this.refresh());
        private final int windowId = WindowIdProvider.next(this);
        private final User<?> viewer;
        private final Connection connection;
        private Title title;

        public Impl(User<?> viewer) {
            this.viewer = viewer;
            this.connection = viewer.connection();
            this.title = Title.fixed(Component.text("Sunscreen Menu #" + windowId));
            open();
        }

        private void open() {
            final WrapperPlayServerOpenWindow openWindow = new WrapperPlayServerOpenWindow(windowId, 8, title.next());
            final WrapperPlayServerWindowItems windowItems = new WrapperPlayServerWindowItems(windowId, 0, contents.pairStream().map(Pair::second).toList().stream().map(item -> ItemStack.builder().amount(item.amount()).type(ItemTypes.getById(ClientVersion.V_1_21_4, item.material().material())).build()).toList(), null);
            connection.send(openWindow);
            connection.send(windowItems);
        }

        private void refresh() {
            final WrapperPlayServerWindowItems windowItems = new WrapperPlayServerWindowItems(windowId, 0, contents.pairStream().map(Pair::second).toList().stream().map(item -> ItemStack.builder().amount(item.amount()).type(ItemTypes.getById(ClientVersion.V_1_21_4, item.material().material())).build()).toList(), null);
            connection.send(windowItems);
        }

        @Override
        public Contents contents() {
            return contents;
        }

        @Override
        public Title title() {
            return title;
        }

        public void title(Title title) {
            this.title = title;
            open();
        }

        @Override
        public void item(Pos2D pos2D, Item item) {
            contents.set(pos2D, item);
        }

        @Override
        public void close() {
            connection.send(new WrapperPlayServerCloseWindow(windowId));
        }

        @Override
        public int windowId() {
            return windowId;
        }

        @Override
        public void click(Pos2D click) {
            if (click.x() < 0 || click.y() < 0) {
                return;
            }
            if (click.x() >= contents.rows().size() || click.y() >= contents.sizeVertical()) {
                return;
            }
            PacketEvents.getAPI().getPlayerManager().receivePacket(viewer.platformSpecificPlayer(), new WrapperPlayClientClickWindow(windowId, Optional.empty(), click.yi()* 9 + click.xi(), 0, Optional.of(0), WrapperPlayClientClickWindow.WindowClickType.PICKUP, Optional.empty(), null));
        }

        //@Override
        //public void click(ServerClickContainer packet) {

        //}
    }

}
