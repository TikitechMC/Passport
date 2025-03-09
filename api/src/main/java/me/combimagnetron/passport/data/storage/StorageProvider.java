package me.combimagnetron.passport.data.storage;

import me.combimagnetron.passport.data.Identifier;
import me.combimagnetron.passport.internal.network.ByteBuffer;

public interface StorageProvider {

    void save(Identifier identifier, ByteBuffer data);

    ByteBuffer load(Identifier identifier);

    void delete(Identifier identifier);

}
