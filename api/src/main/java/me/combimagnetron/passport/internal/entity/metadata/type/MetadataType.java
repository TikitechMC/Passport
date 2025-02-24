package me.combimagnetron.passport.internal.entity.metadata.type;

import com.github.retrooper.packetevents.protocol.entity.data.EntityData;
import com.github.retrooper.packetevents.protocol.entity.data.EntityDataType;

public interface MetadataType<T> {

    byte[] bytes();

    T object();

}
