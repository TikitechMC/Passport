package me.combimagnetron.passport.internal.network.packet;

import me.combimagnetron.passport.internal.network.ByteBuffer;

public interface Type {

    ByteBuffer byteBuffer();

    Byte[] write();

    default <T> T read(ByteBuffer.Adapter<T> type) {
        return byteBuffer().read(type);
    }

    default <T> Type write(ByteBuffer.Adapter<T> type, T object) {
        byteBuffer().write(type, object);
        return this;
    }

}
