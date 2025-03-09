package me.combimagnetron.passport.data;

import me.combimagnetron.passport.internal.network.ByteBuffer;

public record DataObject<V>(ByteBuffer.Adapter<?> type, V value) {

    public static <V> DataObject<V> of(ByteBuffer.Adapter<?> type, V value) {
        return new DataObject<>(type, value);
    }

}
