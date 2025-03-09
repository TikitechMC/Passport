package me.combimagnetron.passport.data.storage.s3;

import me.combimagnetron.passport.data.Identifier;
import me.combimagnetron.passport.data.storage.StorageProvider;
import me.combimagnetron.passport.internal.network.ByteBuffer;

public class S3StorageProvider implements StorageProvider {

    @Override
    public void save(Identifier identifier, ByteBuffer data) {

    }

    @Override
    public ByteBuffer load(Identifier identifier) {
        return null;
    }

    @Override
    public void delete(Identifier identifier) {

    }
}
