package me.combimagnetron.passport.internal.item;

import me.combimagnetron.passport.data.Identifier;
import me.combimagnetron.passport.internal.network.ByteBuffer;

import java.util.LinkedHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public interface ItemComponent<T> {
    LinkedHashMap<Integer, ItemComponentType<?>> COMPONENTS = new LinkedHashMap<>();
    AtomicInteger COMPONENT_ID = new AtomicInteger();
    ItemComponentType<String> STRING = new SimpleItemComponentType<>(Identifier.of("minecraft", ""), String.class, ByteBuffer.Adapter.STRING);
    ItemComponentType<Integer> MAX_STACK_SIZE = new SimpleItemComponentType<>(Identifier.of("minecraft", "max_stack_size"), Integer.class, ByteBuffer.Adapter.INT);
    ItemComponentType<Integer> MAX_DAMAGE_AMOUNT = new SimpleItemComponentType<>(Identifier.of("minecraft", "max_damage"), Integer.class, ByteBuffer.Adapter.INT);
    ItemComponentType<Integer> DAMAGE_AMOUNT = new SimpleItemComponentType<>(Identifier.of("minecraft", "damage"), Integer.class, ByteBuffer.Adapter.INT);
    ItemComponentType<Boolean> UNBREAKABLE = new SimpleItemComponentType<>(Identifier.of("minecraft", "unbreakable"), Boolean.class, ByteBuffer.Adapter.BOOLEAN);
    ItemComponentType<Boolean> CAN_DESTROY = new SimpleItemComponentType<>(Identifier.of("minecraft", "can_destroy"), Boolean.class, ByteBuffer.Adapter.BOOLEAN);
    ItemComponentType<Boolean> CAN_PLACE_ON = new SimpleItemComponentType<>(Identifier.of("minecraft", "can_place_on"), Boolean.class, ByteBuffer.Adapter.BOOLEAN);

    ItemComponentType<T> type();

    T value();

    static <T> ItemComponent<T> of(ItemComponentType<T> type, T value) {
        return new SimpleItemComponent<>(type, value);
    }

    record SimpleItemComponent<T>(ItemComponentType<T> type, T value) implements ItemComponent<T> {

    }

    interface ItemComponentType<T> {
        Identifier identifier();

        Class<T> type();

        ByteBuffer.Adapter<T> adapter();

        default void write(ByteBuffer buffer, T value) {
            adapter().write(buffer.nio(), value);
        }

        default T read(ByteBuffer buffer) {
            return adapter().read(buffer.nio());
        }
    }


    record SimpleItemComponentType<T>(Identifier identifier, Class<T> type, ByteBuffer.Adapter<T> adapter) implements ItemComponentType<T> {

        public SimpleItemComponentType {
            COMPONENTS.put(COMPONENT_ID.getAndIncrement(), this);
        }

    }

}
