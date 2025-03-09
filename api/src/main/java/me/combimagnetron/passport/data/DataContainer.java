package me.combimagnetron.passport.data;

import java.util.Map;
import java.util.UUID;

public interface DataContainer {

    <V> DataObject<V> request(Identifier identifier);

    <V> DataObject<V> add(Identifier identifier, DataObject<V> object);

    <V> Map<Identifier, DataObject<?>> values();

    UUID syncId();

    void syncId(UUID uuid);

    int size();



}
