package me.combimagnetron.passport.internal.menu;

import me.combimagnetron.passport.util.Pair;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class WindowIdProvider {
    private static final Map<Integer, ContainerMenu> MENU_MAP = new HashMap<>();
    private static final AtomicInteger ATOMIC_INTEGER = new AtomicInteger(Integer.MAX_VALUE);

    public static ContainerMenu get(int windowId) {
        return MENU_MAP.get(windowId);
    }

    public static <T extends ContainerMenu> T get(int windowId, Class<T> clazz) {
        return clazz.cast(MENU_MAP.get(windowId));
    }

    public static Collection<Pair<Integer, ContainerMenu>> menus() {
        return MENU_MAP.entrySet().stream().map(entry -> new Pair<>(entry.getKey(), entry.getValue())).toList();
    }

    public static Integer next(ContainerMenu menu) {
        final int id = ATOMIC_INTEGER.decrementAndGet();
        MENU_MAP.put(id, menu);
        return id;
    }
}
