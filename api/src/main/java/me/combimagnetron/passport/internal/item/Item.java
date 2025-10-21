package me.combimagnetron.passport.internal.item;

import me.combimagnetron.passport.internal.item.Material;
import me.combimagnetron.passport.util.data.Identifier;
import net.kyori.adventure.nbt.CompoundBinaryTag;
import net.kyori.adventure.text.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

public interface Item {

    Material material();

    int customModelData();

    Component name();

    int amount();

    CompoundBinaryTag nbt();

    Collection<Component> lore();

    Item component(ItemComponent<?> component);

    static Item item(Material material) {
        return Impl.item(material);
    }

    static Item item(Material material, int amount) {
        return Impl.item(material, amount);
    }

    static Item empty() {
        return Impl.empty();
    }

    class ItemComponentMap {
        private final ConcurrentHashMap<Identifier, ItemComponent<?>> map = new ConcurrentHashMap<>();

        public void put(Identifier key, ItemComponent<?> value) {
            map.put(key, value);
        }

        public <V> ItemComponent<V> get(Identifier key) {
            return (ItemComponent<V>) map.get(key);
        }

        public <V> ItemComponent<V> get(ItemComponent.ItemComponentType<V> type) {
            return (ItemComponent<V>) map.get(type.identifier());
        }

    }

    class Impl implements Item {
        private final static Impl EMPTY = Impl.item(Material.AIR);
        private final ItemComponentMap componentMap = new ItemComponentMap();
        private final List<Component> lore = new ArrayList<>();
        private final CompoundBinaryTag.Builder nbtCompound = CompoundBinaryTag.builder();
        private int amount;
        private final Material material;
        private int customModelData;
        private Component name;

        private Impl(Material material, int amount) {
            this.material = material;
            this.amount = amount;
            nbtCompound.putInt("id", material.material());
            nbtCompound.putByte("count", (byte) amount);
        }

        public static Impl item(Material material) {
            return new Impl(material, 1);
        }

        public static Impl item(Material material, int amount) {
            return new Impl(material, amount);
        }

        public static Impl empty() {
            return EMPTY;
        }

        public Material material() {
            return this.material;
        }

        public int customModelData() {
            return this.customModelData;
        }

        public Component name() {
            return this.name;
        }

        public int amount() {
            return this.amount;
        }

        public Collection<Component> lore() {
            return this.lore;
        }

        @Override
        public Item component(ItemComponent<?> component) {
            componentMap.put(component.type().identifier(), component);
            return this;
        }

        public CompoundBinaryTag nbt() {
            return nbtCompound.build();
        }

        public record Slot(Impl item, int slot) {

        }


    }

}
