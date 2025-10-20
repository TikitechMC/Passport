package me.combimagnetron.passport.internal.menu;

import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerCloseWindow;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerOpenWindow;
import me.combimagnetron.passport.internal.item.Item;
import me.combimagnetron.passport.internal.network.Connection;
import me.combimagnetron.passport.user.User;
import me.combimagnetron.passport.util.Pos2D;
import net.kyori.adventure.text.Component;

public interface AnvilMenu extends ContainerMenu {

    static AnvilMenu of(User<?> viewer) {
        return new Impl(viewer);
    }

    class Impl implements AnvilMenu {
        private final Contents contents = new Contents(c -> this.refresh());
        private final int windowId = WindowIdProvider.next(this);
        private final User<?> viewer;
        private final Connection connection;
        private Title title;

        protected Impl(User<?> viewer) {
            this.viewer = viewer;
            this.connection = viewer.connection();
            this.title = Title.fixed(Component.text("Sunscreen Menu #" + (windowId * -1)));
            open();
        }

        private void open() {
            WrapperPlayServerOpenWindow openWindow = new WrapperPlayServerOpenWindow(windowId, 8, title.next());
            //WrapperPlayServerWindowItems windowItems = new WrapperPlayServerWindowItems(windowId, 0, contents.pairStream().map(Pair::second).toList().stream().map(item -> ItemStack.builder().amount(item.amount()).type(ItemTypes.getById(ClientVersion.V_1_21_4, item.material().material())).build()).toList(), null);
            connection.send(openWindow);
            //connection.send(windowItems);
            //final ClientOpenScreen openScreen = ClientOpenScreen.of(windowId, 5, title.next());
            //final ClientSetScreenContent setScreenContent = ClientSetScreenContent.of(contents.all(), AdapterImpl.empty(), 0, windowId);
            //connection.send(openScreen);
            //connection.send(setScreenContent);
            //refresh();
        }

        private void refresh() {
            //connection.send(ClientBundleDelimiter.bundleDelimiter(contents.pairStream().map(pair -> ClientSetScreenSlot.of(windowId, 0, pair.first().shortValue(), pair.second())).toList().toArray(new ClientSetScreenSlot[0])));
        }

        @Override
        public Contents contents() {
            return contents;
        }

        @Override
        public Title title() {
            return title;
        }

        @Override
        public void title(Title title) {
            this.title = title;
            refresh();
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
    }

}
