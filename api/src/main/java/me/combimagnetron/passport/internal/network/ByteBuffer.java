package me.combimagnetron.passport.internal.network;

import me.combimagnetron.passport.CometBase;
import me.combimagnetron.passport.data.Identifier;
import me.combimagnetron.passport.user.User;
import me.combimagnetron.passport.util.ProtocolUtil;
import me.combimagnetron.passport.util.Values;
import org.jglrxavpok.hephaistos.nbt.CompressedProcesser;
import org.jglrxavpok.hephaistos.nbt.NBTException;
import org.jglrxavpok.hephaistos.nbt.NBTReader;
import org.jglrxavpok.hephaistos.nbt.NBTWriter;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class ByteBuffer {
    private java.nio.ByteBuffer buffer;
    public static ByteBuffer of(byte[] bytes) {
        return new ByteBuffer(bytes);
    }
    public static ByteBuffer empty() {
        return new ByteBuffer();
    }
    private ByteBuffer(byte[] bytes) {
        this.buffer = java.nio.ByteBuffer.wrap(bytes);
    }
    private ByteBuffer() {
        this.buffer = java.nio.ByteBuffer.allocate(700000);
    }
    public <T> ByteBuffer write(Adapter<T> type, T object) {
        type.write(buffer, object);
        return this;
    }

    public ByteBuffer write(byte[] bytes) {
        buffer.put(bytes);
        return this;
    }

    public <T> T read(Adapter<T> type) {
        return type.read(buffer);
    }
    public <T> void writeCollection(Adapter<T> type, Collection<T> collection) {
        write(Adapter.VAR_INT, collection.size());
        for (T t : collection) {
            write(type, t);
        }
    }

    public <T extends Writeable> void writeCollection(Collection<T> collection) {
        write(Adapter.VAR_INT, collection.size());
        for (T t : collection) {
            t.write(this);
        }
    }

    public <T extends Enum<?>> T readEnum(Class<T> clazz) {
        return clazz.getEnumConstants()[read(Adapter.VAR_INT)];
    }

    public <T> Collection<T> readCollection(Function<ByteBuffer, T> function) {
        final int size = read(Adapter.VAR_INT);
        final List<T> values = new java.util.ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            values.add(function.apply(this));
        }
        return values;
    }

    public <T> Collection<T> readCollection(Adapter<T> type, Supplier<Collection<T>> collectionSupplier) {
        Collection<T> collection = collectionSupplier.get();
        int size = read(Adapter.INT);
        for (int i = 0; i < size; i++) {
            collection.add(read(type));
        }
        return collection;
    }

    public java.nio.ByteBuffer nio() {
        return buffer;
    }

    public byte[] bytes() {
        java.nio.ByteBuffer trimmed = trimBuffer(buffer);
        if (!trimmed.hasArray()) {
            byte[] array = new byte[buffer.remaining()];
            trimmed.get(array);
            return array;
        }
        return trimmed.array();
    }

    private static java.nio.ByteBuffer trimBuffer(java.nio.ByteBuffer original) {
        int usedSize = original.position();
        original.flip();
        java.nio.ByteBuffer trimmed = java.nio.ByteBuffer.allocate(usedSize);
        for (int i = 0; i < usedSize; i++) {
            trimmed.put(original.get());
        }
        trimmed.flip();
        return trimmed;
    }

    public interface Writeable {

        void write(final ByteBuffer byteBuffer);

    }

    public interface Adapter<T> {
        Adapter<String> STRING = Impl.of(buffer -> {
            int length = ProtocolUtil.readVarInt(buffer);
            byte[] bytes = new byte[length];
            buffer.get(bytes);
            return new String(bytes);
        }, (buffer, string) -> {
            byte[] bytes = string.getBytes();
            ProtocolUtil.writeVarInt(buffer, bytes.length);
            buffer.put(bytes);
        });
        Adapter<Byte[]> BYTE_ARRAY = Impl.of(input -> {
            int length = input.getInt();
            Byte[] bytes = new Byte[length];
            for (int i = 0; i < length; i++) {
                bytes[i] = input.get();
            }
            return bytes;
        }, (output, bytes) -> {
            output.putInt(bytes.length);
            for (Byte aByte : bytes) {
                output.get(aByte);
            }
        });
        Adapter<Long> LONG = Impl.of(java.nio.ByteBuffer::getLong, java.nio.ByteBuffer::putLong);
        Adapter<Double> DOUBLE = Impl.of(java.nio.ByteBuffer::getDouble, java.nio.ByteBuffer::putDouble);
        Adapter<Float> FLOAT = Impl.of(java.nio.ByteBuffer::getFloat, java.nio.ByteBuffer::putFloat);
        Adapter<Integer> INT = Impl.of(java.nio.ByteBuffer::getInt, java.nio.ByteBuffer::putInt);
        Adapter<User> USER = Impl.of(input -> CometBase.comet().users().deserialize(ByteBuffer.of(STRING.read(input).getBytes())), (output, user) -> output.put(user.serialize().bytes()));
        Adapter<Identifier> IDENTIFIER = Impl.of(input -> {
            String[] parts = STRING.read(input).split(":");
            return Identifier.of(parts[0], parts[1]);
        }, (output, identifier) -> STRING.write(output, identifier.string()));
        Adapter<Type<?>> TYPE = Impl.of(input -> {
            Class<?> clazz;
            try {
                clazz = Class.forName(STRING.read(input));
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
            int size = input.getInt();
            byte[] bytes = new byte[size];
            for (int i = 0; i < size; i++) {
                bytes[i] = input.get();
            }
            try {
                return (Type<?>) clazz.getDeclaredConstructor(byte[].class).newInstance(bytes);
            } catch (InstantiationException | NoSuchMethodException | InvocationTargetException |
                     IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }, (output, type) -> {
            byte[] bytes = type.serialize();
            STRING.write(output, type.type().getName());
            output.put((byte) bytes.length);
            output.put(bytes);
        });
        Adapter<Deployment> DEPLOYMENT = Impl.of(input -> {
            String[] parts = STRING.read(input).split("%");
            return Deployment.of(parts[0], parts[1], Integer.parseInt(parts[2]), Integer.parseInt(parts[3]), Integer.parseInt(parts[4]));
        }, (output, deployment) -> STRING.write(output, deployment.name() + "%" + deployment.image() + "%" + deployment.minReplicas() + "%" + deployment.maxReplicas() + "%" + deployment.playerInstanceThreshold()));
        Adapter<Version> VERSION = Impl.of(input -> {
            String[] parts = STRING.read(input).split("\\.");
            return Version.major(Integer.parseInt(parts[0])).minor(Integer.parseInt(parts[1])).patch(parts[2]);
        }, (output, version) -> STRING.write(output, version.version()));
        Adapter<Integer> UNSIGNED_BYTE = Impl.of(java.nio.ByteBuffer::getInt, java.nio.ByteBuffer::putInt);
        Adapter<BrokerAgreement> BROKER_AGREEMENT = Impl.of(input -> {
            BrokerAgreement agreement = BrokerAgreement.brokerAgreement();
            int interceptSize = input.getInt();
            for (int i = 0; i < interceptSize; i++) {
                agreement.intercept(new BrokerAgreement.MessageReference.InterceptMessageReference<>(ServiceFile.find(STRING.read(input))));
            }
            int monitorSize = input.getInt();
            for (int i = 0; i < monitorSize; i++) {
                agreement.monitor(new BrokerAgreement.MessageReference.MonitorMessageReference<>(ServiceFile.find(STRING.read(input))));
            }
            return agreement;
        }, (output, agreement) -> {
            output.putInt(agreement.interceptSize());
            for (BrokerAgreement.MessageReference.InterceptMessageReference<? extends Message> intercept : agreement.interceptMessages()) {
                STRING.write(output ,intercept.message().getName());
            }
            output.putInt(agreement.monitorSize());
            for (BrokerAgreement.MessageReference.MonitorMessageReference<? extends Message> monitor : agreement.monitorMessages()) {
                STRING.write(output, monitor.message().getName());
            }
        });
        Adapter<Boolean> BOOLEAN = Impl.of(input -> input.get() == 1, (output, bool) -> output.put(bool ? (byte) 1 : (byte) 0));
        Adapter<Byte> BYTE = Impl.of(java.nio.ByteBuffer::get, java.nio.ByteBuffer::put);
        Adapter<Short> SHORT = Impl.of(java.nio.ByteBuffer::getShort, java.nio.ByteBuffer::putShort);
        Adapter<DataObject<?>> DATA_OBJECT = Impl.of(input -> {
            String identifier = STRING.read(input);
            Adapter<?> adapter = Adapter.VALUES.values().stream().filter(a -> a.getClass().getTypeParameters()[0].getBounds()[0].getTypeName().equals(identifier)).findAny().orElseThrow();
            return new DataObject<>(adapter, adapter.read(input));
        }, (output, dataObject) -> {
            STRING.write(output, dataObject.type().getClass().getTypeParameters()[0].getBounds()[0].getTypeName());
            Adapter<Object> adapter = (Adapter<Object>)dataObject.type();
            adapter.write(output, dataObject.value());
        });
        Adapter<UUID> UUID = Impl.of(input -> new UUID(input.getLong(), input.getLong()), (output, uuid) -> {
            output.putLong(uuid.getMostSignificantBits());
            output.putLong(uuid.getLeastSignificantBits());
        });
        Adapter<DataContainer> DATA_CONTAINER = Impl.of(input -> {
            UserDataContainer container = new UserDataContainer();
            java.util.UUID syncId = UUID.read(input);
            int size = input.getInt();
            for (int i = 0; i < size; i++) {
                Identifier key = IDENTIFIER.read(input);
                DataObject<?> dataObject = DATA_OBJECT.read(input);
                container.add(key, dataObject);
            }
            container.syncId(syncId);
            return container;
        }, (output, container) -> {
            UUID.write(output, container.syncId());
            output.putInt(container.size());
            for (Map.Entry<Identifier, DataObject<?>> key : container.values().entrySet()) {
                IDENTIFIER.write(output, key.getKey());
                DATA_OBJECT.write(output, key.getValue());
            }
        });
        Adapter<org.jglrxavpok.hephaistos.nbt.NBT> NBT = Impl.of(
            input -> {
                NBTReader nbtReader = new NBTReader(new InputStream() {
                    @Override
                    public int read() {
                        return input.get() & 0xFF;
                    }
                }, CompressedProcesser.NONE);
                try {
                    return nbtReader.read();
                } catch (IOException | NBTException e) {
                    throw new RuntimeException(e);
                }
            }, (output, value) -> {
                    NBTWriter nbtWriter = new NBTWriter(new OutputStream() {
                        @Override
                        public void write(int b) {
                            output.put((byte) b);
                        }
                    }, CompressedProcesser.NONE);
                    try {
                        nbtWriter.writeNamed("", value);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
            });
        Adapter<Item<?>> ITEM = Impl.of(input -> {
            if (!(input.get() == 0)) {
                return Item.empty();
            }
            int material = ProtocolUtil.readVarInt(input);
            int amount = input.get();
            return Item.item(material, amount);
        }, ((output, item) -> {
            output.put(item != null ? (byte) 1 : (byte) 0);
            if (item == null) {
                return;
            }
            ProtocolUtil.writeVarInt(output, (int) item.material());
            output.put((byte) item.amount());
            if (item.nbt().isEmpty()) {
                output.put((byte) 0);
                return;
            }
            ByteBuffer byteBuffer = ByteBuffer.empty();
            byteBuffer.write(NBT, item.nbt());
            output.put(byteBuffer.bytes());
        }));
        Adapter<Integer> VAR_INT = Impl.of(ProtocolUtil::readVarInt, ProtocolUtil::writeVarInt);
        Adapter<Long> VAR_LONG = Impl.of(ProtocolUtil::readVarLong, ProtocolUtil::writeVarLong);
        Values<Adapter<?>> VALUES = Values.of(STRING, TYPE, LONG, DOUBLE, FLOAT, INT, IDENTIFIER, DEPLOYMENT, UNSIGNED_BYTE, BOOLEAN, BYTE, SHORT, DATA_OBJECT, UUID, NBT, ITEM, VAR_INT, VAR_LONG);
        T read(java.nio.ByteBuffer byteArrayDataInput);
        void write(java.nio.ByteBuffer output, T object);
        final class Impl<V> implements Adapter<V> {
            private final Function<java.nio.ByteBuffer, V> readFunction;
            private final BiConsumer<java.nio.ByteBuffer, V> writeConsumer;
            private Impl(Function<java.nio.ByteBuffer, V> readFunction, BiConsumer<java.nio.ByteBuffer, V> writeConsumer) {
                this.readFunction = readFunction;
                this.writeConsumer = writeConsumer;
            }
            public static <V> Impl<V> of(Function<java.nio.ByteBuffer, V> readFunction, BiConsumer<java.nio.ByteBuffer, V> writeConsumer) {
                return new Impl<>(readFunction, writeConsumer);
            }
            @Override
            public V read(java.nio.ByteBuffer byteArrayDataInput) {
                return readFunction.apply(byteArrayDataInput);
            }
            @Override
            public void write(java.nio.ByteBuffer output, V object) {
                writeConsumer.accept(output, object);
            }
        }

    }
}