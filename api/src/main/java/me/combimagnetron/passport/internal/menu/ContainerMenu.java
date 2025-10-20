package me.combimagnetron.passport.internal.menu;

import me.combimagnetron.passport.internal.item.Item;
import me.combimagnetron.passport.util.Pos2D;

public interface ContainerMenu {

    Contents contents();

    Title title();

    void title(Title title);

    void item(Pos2D pos2D, Item item);

    void close();

    int windowId();

}
