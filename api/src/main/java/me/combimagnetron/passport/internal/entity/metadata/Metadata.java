package me.combimagnetron.passport.internal.entity.metadata;

import com.github.retrooper.packetevents.protocol.entity.data.EntityData;
import com.github.retrooper.packetevents.protocol.entity.data.EntityDataType;
import com.github.retrooper.packetevents.protocol.entity.data.EntityDataTypes;
import me.combimagnetron.passport.internal.entity.metadata.type.*;
import me.combimagnetron.passport.internal.entity.metadata.type.Boolean;
import me.combimagnetron.passport.internal.entity.metadata.type.Byte;
import me.combimagnetron.passport.internal.entity.metadata.type.Float;
import me.combimagnetron.passport.internal.entity.metadata.type.String;
import me.combimagnetron.passport.internal.network.ByteBuffer;
import me.combimagnetron.passport.util.Pair;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public interface Metadata {
    Template BASE = Template.of(
            Pair.of(0 ,Byte.class),
            Pair.of(1 ,VarInt.class),
            Pair.of(2 ,OptChat.class),
            Pair.of(3 ,Boolean.class),
            Pair.of(4 ,Boolean.class),
            Pair.of(5 ,Boolean.class),
            Pair.of(6 ,Pose.class),
            Pair.of(7 ,VarInt.class));

    Factory FACTORY = new Factory();

    Holder holder();

    ByteBuffer bytes();

    List<EntityData> entityData();

    @SafeVarargs
    static Metadata inheritAndMerge(Metadata metadata, Pair<Integer, MetadataType<?>>... types) {
        return FACTORY.inheritAndMerge(metadata, Arrays.stream(types).map(pair -> new MetadataPair(pair.first(), pair.second())).toArray(MetadataPair[]::new));
    }

    static Metadata merge(Metadata... metadata) {
        return FACTORY.merge(metadata);
    }

    @SafeVarargs
    static Metadata of(Pair<Integer, MetadataType<?>>... types) {
        return FACTORY.of(Arrays.stream(types).map(pair -> new MetadataPair(pair.first(), pair.second())).toArray(MetadataPair[]::new));
    }

    interface Template {

        Collection<Pair<Integer, Class<? extends MetadataType<?>>>> typeClasses();

        @SafeVarargs
        static Template of(Pair<Integer, Class<? extends MetadataType<?>>>... classes) {
            return new Impl(classes);
        }


        Metadata apply(Pair<Integer, MetadataType<?>>... types);

        final class Impl implements Template {
            private final Collection<Pair<Integer, Class<? extends MetadataType<?>>>> classes = new HashSet<>();

            @SafeVarargs
            private Impl(Pair<Integer, Class<? extends MetadataType<?>>>... classes) {
                this.classes.addAll(List.of(classes));
            }

            @Override
            public Collection<Pair<Integer, Class<? extends MetadataType<?>>>> typeClasses() {
                return classes;
            }

            @Override
            public Metadata apply(Pair<Integer, MetadataType<?>>... types) {
                return FACTORY.of(Arrays.stream(types).map(type -> new MetadataPair(type.first(), type.second())).toArray(MetadataPair[]::new));
            }
        }

    }

    record MetadataPair(int index, MetadataType<?> type) {
        private static final Map<Class<? extends MetadataType<?>>, Integer> TYPE_MAP = new HashMap<>();

        static {
            TYPE_MAP.put(Byte.class, 0);
            TYPE_MAP.put(VarInt.class, 1);
            TYPE_MAP.put(VarLong.class, 2);
            TYPE_MAP.put(Float.class, 3);
            TYPE_MAP.put(String.class, 4);
            TYPE_MAP.put(Chat.class, 5);
            TYPE_MAP.put(OptChat.class, 6);
            TYPE_MAP.put(Slot.class, 7);
            TYPE_MAP.put(Boolean.class, 8);
            TYPE_MAP.put(Rotation.class, 9);
            TYPE_MAP.put(Position.class, 10);
            TYPE_MAP.put(OptPosition.class, 11);
            TYPE_MAP.put(Pose.class, 20);
            TYPE_MAP.put(Vector3d.class, 26);
            TYPE_MAP.put(Quaternion.class, 27);
        }

        public static MetadataPair metadataPair(int index, MetadataType<?> type) {
            return new MetadataPair(index, type);
        }

        public static MetadataPair metadataPair(MetadataType<?> type) {
            return new MetadataPair(TYPE_MAP.get(type.getClass()), type);
        }
    }


    final class Holder {
        private final ConcurrentHashMap<Integer, MetadataPair> metadataTypes = new ConcurrentHashMap<>();
        private int i = 0;

        public void put(MetadataPair type) {
            metadataTypes.put(type.index, type);
        }

    }

    final class Impl implements Metadata {
        private final static HashMap<Class<? extends MetadataType<?>>, EntityDataType<?>> TYPES = new HashMap<>();
        private final Holder holder = new Holder();

        static {
            TYPES.put(Boolean.class, EntityDataTypes.BOOLEAN);
            TYPES.put(Byte.class, EntityDataTypes.BYTE);
            TYPES.put(Chat.class, EntityDataTypes.ADV_COMPONENT);
            TYPES.put(Float.class, EntityDataTypes.FLOAT);
            TYPES.put(OptChat.class, EntityDataTypes.OPTIONAL_ADV_COMPONENT);
            TYPES.put(Pose.class, EntityDataTypes.ENTITY_POSE);
            TYPES.put(Position.class, EntityDataTypes.BLOCK_POSITION);
            TYPES.put(Rotation.class, EntityDataTypes.ROTATION);
            TYPES.put(String.class, EntityDataTypes.STRING);
            TYPES.put(Vector3d.class, EntityDataTypes.VECTOR3F);
            TYPES.put(VarInt.class, EntityDataTypes.INT);
            TYPES.put(VarLong.class, EntityDataTypes.LONG);
            TYPES.put(Slot.class, EntityDataTypes.ITEMSTACK);
            TYPES.put(Quaternion.class, EntityDataTypes.QUATERNION);
        }

        @Override
        public Holder holder() {
            return holder;
        }

        @Override
        public ByteBuffer bytes() {
            final ByteBuffer buffer = ByteBuffer.empty();
            holder().metadataTypes.forEach((index, type) -> {
                buffer.write(ByteBuffer.Adapter.UNSIGNED_BYTE, index);
                buffer.write(ByteBuffer.Adapter.VAR_INT, type.index);
                buffer.write(type.type.bytes());
            });
            return buffer;
        }

        @Override
        public List<EntityData> entityData() {
            final List<EntityData> data = new ArrayList<>();
            holder.metadataTypes.forEach((index, type) -> {
                data.add(new EntityData(index, TYPES.get(type.type.getClass()), type.type.object()));
            });
            return data;
        }
    }

    final class Factory {

        public Metadata merge(Metadata... metadata) {
            final Impl impl = new Impl();
            Arrays.stream(metadata).forEachOrdered(m -> {
                Impl impl1 = (Impl) m;
                impl.holder().metadataTypes.putAll(impl1.holder().metadataTypes);
            });
            return impl;
        }

        public Metadata inheritAndMerge(Metadata metadata, MetadataPair... metadataTypes) {
            for (MetadataPair metadataType : metadataTypes) {
                metadata.holder().put(metadataType);
            }
            return metadata;
        }

        public Metadata of(MetadataPair... types) {
            final Impl impl = new Impl();
            for (MetadataPair metadataType : types) {
                impl.holder().put(metadataType);
            }
            return impl;
        }


    }

}