package me.combimagnetron.passport.generator;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;
import me.combimagnetron.passport.internal.network.ByteBuffer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class PacketGenerator {
    private final List<JsonPacket> items;

    public PacketGenerator(File file, String version) {
        try {
            items = new Gson().fromJson(new FileReader(file), new TypeToken<ArrayList<JsonPacket>>() {}.getType());
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        write(version);
        System.out.println("Generated packets for version " + version);
    }

    private void write(String version) {
        for (JsonPacket item : items) {
            StringBuilder builder = new StringBuilder();
            builder.append(metadata());
            builder.append(header(version, item.packet(), item.boundTo(), item.protocolState(), item));
            builder.append(content(item));
            builder.append("}");
            try {
                Util.write("api/src/main/java/me/combimagnetron/generated/R" + version.replaceAll("\\.", "_") + "/packet/" + item.protocolState +"/" + Util.capitalizeFirstLetter(item.boundTo()) + Util.snakeToUpperCamel(item.packet()) + ".java", builder.toString());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    private String metadata() {
        return """
        // THIS FILE IS GENERATED FROM MINECRAFT DATA, DO NOT EDIT
        // https://github.com/Cosmorise/Passport/generator/src/main/java/me/combimagnetron/passport/generator/BlockGenerator.java
        """;
    }

    private String content(JsonPacket item) {
        final String name = Util.capitalizeFirstLetter(item.boundTo()) + Util.snakeToUpperCamel(item.packet());
        StringBuilder builder = new StringBuilder();
        builder.append("    private final static int ID = ").append(item.id()).append(";\n");
        builder.append("    private final ByteBuffer byteBuffer;\n");
        for (PacketField field : item.content()) {
            builder.append("    private final ").append(TypeAdapter.find(field.type()).clazz()).append(" ").append(field.field()).append(";\n");
        }
        builder.append("\n");
        builder.append("    public static ").append(name).append(" ").append(Util.lowercaseFirstLetter(Util.snakeToUpperCamel(item.packet()))).append("(");
        for (int i = 0; i < item.content().size(); i++) {
            PacketField field = item.content().get(i);
            TypeAdapter<?> type = TypeAdapter.find(field.type());
            builder.append(type.clazz()).append(" ").append(field.field());
            if (i != item.content().size() - 1) {
                builder.append(", ");
            }
        }
        builder.append(") {\n");
        builder.append("        return new ").append(name).append("(");
        for (int i = 0; i < item.content().size(); i++) {
            PacketField field = item.content().get(i);
            builder.append(field.field());
            if (i != item.content().size() - 1) {
                builder.append(", ");
            }
        }
        builder.append(");\n");
        builder.append("    }\n");
        builder.append("\n");
        builder.append("    private ").append(name).append("(");
        for (int i = 0; i < item.content().size(); i++) {
            PacketField field = item.content().get(i);
            builder.append(TypeAdapter.find(field.type()).clazz()).append(" ").append(field.field());
            if (i != item.content().size() - 1) {
                builder.append(", ");
            }
        }
        builder.append(") {\n");
        builder.append("        this.byteBuffer = ByteBuffer.empty();\n");
        for (PacketField field : item.content()) {
            builder.append("        this.").append(field.field()).append(" = ").append(field.field()).append(";\n");
        }
        builder.append("    }\n");
        builder.append("\n");
        builder.append("    private ").append(name).append("(ByteBuffer buffer) {\n");
        builder.append("        this.byteBuffer = buffer;\n");
        for (PacketField field : item.content()) {
            builder.append("        this.").append(field.field()).append(" = ").append("byteBuffer.read(ByteBuffer.Adapter.").append(TypeAdapter.find(field.type()).byteBuffer()).append(");\n");
        }
        builder.append("    }\n");
        for (PacketField field : item.content()) {
            builder.append("\n");
            builder.append("    public ").append(TypeAdapter.find(field.type()).clazz()).append(" ").append(field.field()).append("() {\n");
            builder.append("        return ").append(field.field()).append(";\n");
            builder.append("    }\n");
        }
        for (PacketField field : item.content()) {
            if (!(TypeAdapter.find(field.type()) instanceof TypeAdapter.Array<?> array)) {
                continue;
            }
            builder.append("\n");
            builder.append(array.content());
        }
        builder.append("\n");
        builder.append("    @Override\n");
        builder.append("    public ByteBuffer byteBuffer() {\n");
        builder.append("        return byteBuffer;\n");
        builder.append("    }\n");
        builder.append("\n");
        builder.append("    @Override\n");
        builder.append("    public byte[] write() {\n");
        builder.append("        byteBuffer.write(ByteBuffer.Adapter.VAR_INT, ID);\n");
        for (PacketField field : item.content()) {
            builder.append("        byteBuffer.write(ByteBuffer.Adapter.").append(TypeAdapter.find(field.type()).byteBuffer()).append(", ").append(field.field()).append(");\n");
        }
        builder.append("        return byteBuffer.bytes();\n");
        builder.append("    }\n");
        return builder.toString();
    }

    private String header(String version, String packet, String boundTo, String protocolState, JsonPacket item) {
        String firstLine = "package me.combimagnetron.generated.R" + version.replaceAll("\\.", "_") + ".packet." + protocolState + ";\n";
        StringBuilder builder = new StringBuilder();
        List<String> imports = new ArrayList<>();
        for (PacketField field : item.content()) {
            Class<?> clazz = TypeAdapter.find(field.type()).type();
            if (clazz == null) {
                continue;
            }
            if (clazz.getName().split("\\.").length < 2) {
                continue;
            }
            imports.add(clazz.getName());
        }
        for (String anImport : imports) {
            builder.append("import ").append(anImport).append(";\n");
        }
        return firstLine + builder.toString() + """
       import me.combimagnetron.passport.internal.network.ByteBuffer;
       import me.combimagnetron.passport.internal.network.packet.Type;
       import me.combimagnetron.passport.util.*;
       \s
       import java.util.ArrayList;
       import java.util.List;
       import java.util.Map;
       \s
       public class\s""" + Util.capitalizeFirstLetter(boundTo) + Util.snakeToUpperCamel(packet) + " implements me.combimagnetron.passport.internal.network.packet." + Util.capitalizeFirstLetter(boundTo) + "Packet" + " {\n";
    }

    static final class JsonPacket {
        private final String id;
        private final String header;
        private final String packet;
        @SerializedName("protocol_state")
        private final String protocolState;
        @SerializedName("bound_to")
        private final String boundTo;
        private final List<PacketField> content;

        JsonPacket(
                String id,
                String header,
                String packet,
                String protocolState,
                String boundTo,
                List<PacketField> content
        ) {
            this.id = id;
            this.header = header;
            this.packet = packet;
            this.protocolState = protocolState;
            this.boundTo = boundTo;
            this.content = content;
        }

        public String id() {
            return id;
        }

        public String header() {
            return header;
        }

        public String packet() {
            return packet;
        }

        @SerializedName("protocol_state")
        public String protocolState() {
            return protocolState;
        }

        @SerializedName("bound_to")
        public String boundTo() {
            return boundTo;
        }

        public List<PacketField> content() {
            return content;
        }

    }

    static final class PacketField {
        private final String field;
        private final String type;

        PacketField(
                String field,
                String type
        ) {
            this.field = field;
            this.type = type;
        }

        public String field() {
            return field;
        }

        public String type() {
            return type;
        }

    }

}
