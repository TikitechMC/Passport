package me.combimagnetron.passport.internal.network;

import me.combimagnetron.generated.R1_21.item.Material;
import me.combimagnetron.passport.Passport;
import me.combimagnetron.passport.data.Identifier;
import me.combimagnetron.passport.internal.entity.metadata.Metadata;
import me.combimagnetron.passport.internal.entity.metadata.type.Vector3d;
import me.combimagnetron.passport.internal.item.Item;
import me.combimagnetron.passport.internal.network.packet.Type;
import me.combimagnetron.passport.user.User;
import me.combimagnetron.passport.util.ProtocolUtil;
import me.combimagnetron.passport.util.Values;
import me.combimagnetron.passport.util.VarInt;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer;
import org.jglrxavpok.hephaistos.nbt.CompressedProcesser;
import org.jglrxavpok.hephaistos.nbt.NBTException;
import org.jglrxavpok.hephaistos.nbt.NBTReader;
import org.jglrxavpok.hephaistos.nbt.NBTWriter;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.*;
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
            int length = VarInt.of(buffer).value();
            byte[] bytes = new byte[length];
            buffer.get(bytes);
            return new String(bytes);
        }, (buffer, string) -> {
            byte[] bytes = string.getBytes();
            VarInt.of(bytes.length).write(buffer);
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
        Adapter<Metadata> METADATA = Impl.of(input -> {
            int length = VarInt.of(input).value();
            byte[] bytes = new byte[length];
            input.get(bytes);
            return null;//Metadata.FACTORY.of(bytes);
        }, (output, metadata) -> {
            byte[] bytes = metadata.bytes().bytes();
            VarInt.of(bytes.length).write(output);
            output.put(bytes);
        });
        Adapter<Integer> UNSIGNED_BYTE = Impl.of(buffer -> buffer.get() & 0xFF, (buffer, integer) -> buffer.put((byte) (integer & 0xFF)));
        Adapter<Long> LONG = Impl.of(java.nio.ByteBuffer::getLong, java.nio.ByteBuffer::putLong);
        Adapter<Double> DOUBLE = Impl.of(java.nio.ByteBuffer::getDouble, java.nio.ByteBuffer::putDouble);
        Adapter<Float> FLOAT = Impl.of(java.nio.ByteBuffer::getFloat, java.nio.ByteBuffer::putFloat);
        Adapter<Integer> INT = Impl.of(java.nio.ByteBuffer::getInt, java.nio.ByteBuffer::putInt);
        Adapter<User<?>> USER = Impl.of(input -> Passport.passport().users().deserialize(ByteBuffer.of(STRING.read(input).getBytes())), (output, user) -> output.put(user.serialize().bytes()));
        Adapter<Identifier> IDENTIFIER = Impl.of(input -> {
            String[] parts = STRING.read(input).split(":");
            return Identifier.of(parts[0], parts[1]);
        }, (output, identifier) -> STRING.write(output, identifier.string()));
        Adapter<Boolean> BOOLEAN = Impl.of(input -> input.get() == 1, (output, bool) -> output.put(bool ? (byte) 1 : (byte) 0));
        Adapter<Byte> BYTE = Impl.of(java.nio.ByteBuffer::get, java.nio.ByteBuffer::put);
        Adapter<Short> SHORT = Impl.of(java.nio.ByteBuffer::getShort, java.nio.ByteBuffer::putShort);
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
        Adapter<UUID> UUID = Impl.of(input -> new UUID(input.getLong(), input.getLong()), (output, uuid) -> {
            output.putLong(uuid.getMostSignificantBits());
            output.putLong(uuid.getLeastSignificantBits());
        });
        Adapter<Item> ITEM = Impl.of(input -> {
            if (!(input.get() == 0)) {
                return Item.empty();
            }
            int material = VarInt.of(input).value();
            int amount = input.get();
            return Item.item(Material.direct(material), amount);
        }, ((output, item) -> {
            output.put(item != null ? (byte) 1 : (byte) 0);
            if (item == null) {
                return;
            }
            VarInt.of(item.material().material()).write(output);
            output.put((byte) item.amount());
            if (item.nbt().isEmpty()) {
                output.put((byte) 0);
                return;
            }
            ByteBuffer byteBuffer = ByteBuffer.empty();
            byteBuffer.write(NBT, item.nbt());
            output.put(byteBuffer.bytes());
        }));
        Adapter<Enum> ENUM = Impl.of(input -> {
            int ordinal = VarInt.of(input).value();
            return null;
        }, (output, anEnum) -> {
            VarInt.of(anEnum.ordinal()).write(output);
        });
        Adapter<Integer> UNSIGNED_SHORT = Impl.of(buffer -> buffer.getShort() & 0xFFFF, (buffer, integer) -> buffer.putShort((short) (integer & 0xFFFF)));
        Adapter<Optional<Integer>> OPTIONAL_VAR_INT = Impl.of(input -> {
            if (input.get() == 0) {
                return Optional.empty();
            }
            return Optional.of(VarInt.of(input).value());
        }, (output, optionalInt) -> {
            if (optionalInt.isEmpty()) {
                output.put((byte) 0);
                return;
            }
            output.put((byte) 1);
            VarInt.of(optionalInt.get()).write(output);
        });
        Adapter<Optional<String>> OPTIONAL_STRING = Impl.of(input -> {
            if (input.get() == 0) {
                return Optional.empty();
            }
            return Optional.of(STRING.read(input));
        }, (output, optionalString) -> {
            if (optionalString.isEmpty()) {
                output.put((byte) 0);
                return;
            }
            output.put((byte) 1);
            STRING.write(output, optionalString.get());
        });
        Adapter<Vector3d> BLOCK_POSITION = Impl.of(input -> {
            final long value = input.getLong();
            final int x = (int) (value >> 38);
            final int y = (int) (value << 52 >> 52);
            final int z = (int) (value << 26 >> 38);
            return Vector3d.vec3(x, y, z);
        }, (output, vector3d) -> {
            final int blockX = (int) vector3d.x();
            final int blockY = (int) vector3d.y();
            final int blockZ = (int) vector3d.z();
            final long longPos = (((long) blockX & 0x3FFFFFF) << 38) |
                    (((long) blockZ & 0x3FFFFFF) << 12) |
                    ((long) blockY & 0xFFF);
            output.putLong(longPos);
        });
        Adapter<Optional<UUID>> OPTIONAL_UUID = Impl.of(input -> {
            if (input.get() == 0) {
                return Optional.empty();
            }
            return Optional.of(UUID.read(input));
        }, (output, optionalUUID) -> {
            if (optionalUUID.isEmpty()) {
                output.put((byte) 0);
                return;
            }
            output.put((byte) 1);
            UUID.write(output, optionalUUID.get());
        });
        Adapter<Type> TYPE = Impl.of(input -> {
            final String typeName = STRING.read(input);
            final Type type = null;//Type.find(typeName);
            if (type == null) {
                throw new IllegalArgumentException("Unknown type: " + typeName);
            }
            return type;
        }, (output, type) -> {
            BYTE_ARRAY.write(output, type.write());
        });
        Adapter<Optional<Component>> OPTIONAL_COMPONENT = Impl.of(input -> {
            if (input.get() == 0) {
                return Optional.empty();
            }
            return Optional.of(GsonComponentSerializer.gson().deserialize(STRING.read(input)));
        }, (output, optionalComponent) -> {
            if (optionalComponent.isEmpty()) {
                output.put((byte) 0);
                return;
            }
            output.put((byte) 1);
            STRING.write(output, GsonComponentSerializer.gson().serialize(optionalComponent.get()));
        });
        Adapter<Integer> VAR_INT = Impl.of(input -> VarInt.of(input).value(), (output, i) -> VarInt.of(i).write(output));
        Adapter<Long> VAR_LONG = Impl.of(ProtocolUtil::readVarLong, ProtocolUtil::writeVarLong);
        Values<Adapter<?>> VALUES = Values.of(STRING, METADATA, LONG, DOUBLE, USER, FLOAT, INT, IDENTIFIER, BOOLEAN, BYTE, SHORT, UUID, NBT, ITEM, VAR_INT, VAR_LONG);
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