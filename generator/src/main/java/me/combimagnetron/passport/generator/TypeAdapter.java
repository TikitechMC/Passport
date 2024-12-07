package me.combimagnetron.passport.generator;

import me.combimagnetron.passport.data.Identifier;
import me.combimagnetron.passport.internal.entity.metadata.Metadata;
import me.combimagnetron.passport.internal.entity.metadata.type.Vector3d;
import me.combimagnetron.passport.util.Pair;
import me.combimagnetron.passport.util.Values;
import me.combimagnetron.passport.util.VarInt;
import me.combimagnetron.passport.util.matcher.MatcherSection;
import me.combimagnetron.passport.util.matcher.MatcherToken;
import me.combimagnetron.passport.util.matcher.Token;
import me.combimagnetron.passport.util.matcher.TokenMatcher;

import java.util.*;
import java.util.function.Supplier;

public interface TypeAdapter<T> {
    TypeAdapter<Byte> BYTE = new Impl<>(byte.class, "byte", "BYTE");
    TypeAdapter<Short> SHORT = new Impl<>(short.class, "short", "SHORT");
    TypeAdapter<Integer> INT = new Impl<>(int.class, "int", "INT");
    TypeAdapter<Long> LONG = new Impl<>(long.class, "long", "LONG");
    TypeAdapter<Float> FLOAT = new Impl<>(float.class, "float", "FLOAT");
    TypeAdapter<Double> DOUBLE = new Impl<>(double.class, "double", "DOUBLE");
    TypeAdapter<String> STRING = new Impl<>(String.class, "string", "STRING");
    TypeAdapter<Byte[]> BYTE_ARRAY = new Impl<>(Byte[].class, "byte_array", "BYTE_ARRAY");
    TypeAdapter<Boolean> BOOLEAN = new Impl<>(boolean.class, "boolean", "BOOLEAN");
    TypeAdapter<Integer> VAR_INT = new Impl<>(Integer.class, "var_int", "VAR_INT");
    TypeAdapter<UUID> UUID = new Impl<>(UUID.class, "uuid", "UUID");
    TypeAdapter<Optional> OPTIONAL_COMPONENT = new Impl<>(Optional.class, "optional_component", "OPTIONAL_COMPONENT");
    TypeAdapter<Optional> OPTIONAL_STRING = new Impl<>(Optional.class, "optional_string", "OPTIONAL_STRING");
    TypeAdapter<Optional> UUID_OPTIONAL = new Impl<>(Optional.class, "optional_uuid", "OPTIONAL_UUID");
    /*Implement*/TypeAdapter<Optional> OPTIONAL_BYTE_ARRAY = new Impl<>(Optional.class, "optional_byte_array", "OPTIONAL_BYTE_ARRAY");
    TypeAdapter<Optional> OPTIONAL_VAR_INT = new Impl<>(Optional.class, "optional_var_int", "OPTIONAL_VAR_INT");
    TypeAdapter<Metadata> METADATA = new Impl<>(Metadata.class, "entity_metadata", "METADATA");
    TypeAdapter<Vector3d> BLOCK_POSITION = new Impl<>(Vector3d.class, "position", "BLOCK_POSITION");
    TypeAdapter<Integer> UNSIGNED_SHORT = new Impl<>(int.class, "unsigned_short", "UNSIGNED_SHORT");
    TypeAdapter<Identifier> IDENTIFIER = new Impl<>(Identifier.class, "identifier", "IDENTIFIER");
    TypeAdapter<Enum> ENUM = new Impl<>(Enum.class, "var_int_enum", "ENUM");
    Values<TypeAdapter<?>> VALUES = Values.of(BYTE, OPTIONAL_VAR_INT, METADATA, IDENTIFIER, SHORT, INT, LONG, FLOAT, DOUBLE, STRING, BYTE_ARRAY, BOOLEAN, VAR_INT, UUID, OPTIONAL_COMPONENT, OPTIONAL_STRING, UUID_OPTIONAL, BLOCK_POSITION, UNSIGNED_SHORT, ENUM);

    //Enum and optional to implementations instead of this shit
    //Array byteBuffer type
    //ENUM types, Json Text Component, Optional datatypes, NBT, BitSet, Identifier, Optional Text Component, Unsigned Byte, angle, Slot
    //Login ClientLoginSuccess Propetry
    //Config ClientRegistryData             fix registry array
    //Config ClientUpdateTags,              fix tags array
    //Config ClientClientboundKnownPacks    fix known packs array
    //Config ClientCustomReportDetails,     fix details array
    //Config ClientServerLinks,             fix links array
    //Config ServerboundKnownPacks          fix known packs array
    //Play ClientAwardStatistics,           fix statistics array
    //Play ClientBossBar                    fix action thing
    //Play ClientChunkBiomes                fix chunk biome data array
    //Play ClientCommandSuggestionsResponse fix matches array

    //FORMAT: array(match:string,has_tooltip:boolean,tooltip:optional_text_component)


    /**TODO: https://wiki.vg/Protocol#Login_Success
    Add more types (https://wiki.vg/Data_types#Identifier, https://wiki.vg/Data_types#NBT, https://wiki.vg/Data_types#Type:BitSet)
    Fix packets.json generator (https://github.com/DockyardMC/MinecraftPacketData/blob/main/1.21/packets.json & https://github.com/DockyardMC/PacketGenerators), it skips the first field and does not understand segmented tables (https://wiki.vg/Protocol#Boss_Bar).
    TODO: add entity system from entities.json
     */

    static TypeAdapter<?> find(String typeName) {
        //.orElseThrow((Supplier<Throwable>) () -> new Exception("Unknown type: " + typeName));
        //
        TokenMatcher matcher = TokenMatcher.matcher(typeName);
        matcher.section(MatcherSection.section().token(MatcherToken.required(Token.Type.of("(array)\\((([a-z]\\w+:[a-z]\\w+)*,*)+\\)"))));
        if (!matcher.validate().empty()) {
            List<? extends Pair<? extends TypeAdapter<?>, String>> types = Arrays.stream(matcher.validate().content(0).replaceAll("array\\(", "").split(",")).map(type -> {
                String[] split = type.split(":");
                return (Pair<? extends TypeAdapter<?>, String>) new Pair<>(VALUES.values().stream().filter(typeAdapter -> typeAdapter.typeName().equals(split[1])).findFirst().orElse(TypeAdapter.STRING), split[0]);
            }).toList();
            return new Array<>(typeName, "BYTE_ARRAY", (Collection<Pair<TypeAdapter<?>, String>>) types);
        }
        return VALUES.values().stream().filter(typeAdapter -> typeAdapter.typeName().equals(typeName)).findFirst().orElse(TypeAdapter.STRING);
    }

    Class<T> type();

    default String clazz() {
        return type().getSimpleName();
    }

    String typeName();

    String byteBuffer();

    record Impl<T>(Class<T> type, String typeName, String byteBuffer) implements TypeAdapter<T> {
    }

    final class Array<T> implements TypeAdapter<T> {
        private final String typeName;
        private final String byteBuffer;
        private final Collection<Pair<TypeAdapter<?>, String>> types;

        public Array(String typeName, String byteBuffer, Collection<Pair<TypeAdapter<?>, String>> types) {
            this.typeName = typeName.split(":")[0];
            this.byteBuffer = byteBuffer;
            this.types = types;
        }

        public String content() {
            StringBuilder record = new StringBuilder();
            record.append("    public record ").append(typeName).append("(");
            for (Pair<TypeAdapter<?>, String> type : types) {
                record.append(type.first().type().getSimpleName()).append(" ").append(type.second()).append(", ");
            }
            record.append("ByteBuffer buffer) implements Type {\n");
            record.append("    ").append("    public static ").append(typeName).append(" ").append(Util.lowercaseFirstLetter(Util.snakeToUpperCamel(typeName))).append("(");
            for (int i = 0; i < types.size(); i++) {
                Pair<TypeAdapter<?>, String> type = (Pair<TypeAdapter<?>, String>) types.toArray()[i];
                record.append(type.first().type().getSimpleName()).append(" ").append(type.second());
                if (i != types.size() - 1) {
                    record.append(", ");
                }
            }
            record.append(") {\n");
            record.append("    ").append("        return new ").append(typeName).append("(");
            for (int i = 0; i < types.size(); i++) {
                Pair<TypeAdapter<?>, String> type = (Pair<TypeAdapter<?>, String>) types.toArray()[i];
                record.append(type.second());
                if (i != types.size() - 1) {
                    record.append(", ");
                }
            }
            record.append(", ByteBuffer.empty());\n");
            record.append("    ").append("    }\n");
            record.append("\n");
            record.append("    ").append("    public static ").append(typeName).append(" ").append(Util.lowercaseFirstLetter(Util.snakeToUpperCamel(typeName))).append("(ByteBuffer buffer) {\n");
            record.append("    ").append("        return new ").append(typeName);
            for (int i = 0; i < types.size(); i++) {
                Pair<TypeAdapter<?>, String> type = (Pair<TypeAdapter<?>, String>) types.toArray()[i];
                if (i == 0) {
                    record.append("(");
                } else {
                    record.append(", ");
                }
                record.append("buffer.read(ByteBuffer.Adapter.").append(type.first().byteBuffer()).append(")");
            }
            record.append("""
                    @Override
                            public ByteBuffer byteBuffer() {
                                return buffer;
                            }""");
            record.append(", buffer);\n");
            record.append("    ").append("    }\n");
            record.append("    ").append("\n");
            record.append("    ").append("}\n");
            return record.toString();
        }

        @Override
        public String clazz() {
            return typeName;
        }

        @Override
        public Class<T> type() {
            return null;
        }

        @Override
        public String typeName() {
            return typeName;
        }

        @Override
        public String byteBuffer() {
            return byteBuffer;
        }
    }
}
