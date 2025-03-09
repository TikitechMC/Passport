package me.combimagnetron.passport.data.impl;

import me.combimagnetron.passport.data.DataContainer;
import me.combimagnetron.passport.data.DataObject;
import me.combimagnetron.passport.data.Identifier;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class UserDataContainer implements DataContainer {
    private final Map<Identifier, DataObject<?>> localStorage = new ConcurrentHashMap<>();
    private UUID syncId = UUID.randomUUID();

    @Override
    public <V> DataObject<V> request(Identifier identifier) {
        return (DataObject<V>) localStorage.get(identifier);
    }

    @Override
    public <V> DataObject<V> add(Identifier identifier, DataObject<V> object) {
        return (DataObject<V>) localStorage.put(identifier, object);
    }

    @Override
    public Map<Identifier, DataObject<?>> values() {
        return localStorage;
    }

    @Override
    public int size() {
        return localStorage.size();
    }

    public UUID syncId() {
        return syncId;
    }

    public void syncId(UUID uuid) {
        this.syncId = uuid;
    }

}
