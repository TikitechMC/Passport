package me.combimagnetron.passport.generator;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class ItemGenerator {
    private final List<JsonItem> items;

    public ItemGenerator(File file) {
        try {
            items = new Gson().fromJson(new FileReader(file), new TypeToken<ArrayList<JsonItem>>() {}.getType());
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        //System.out.println(metadata() + header() + content() + "}");
        try {
            Util.write("api/src/main/java/me/combimagnetron/generated/item/Material.java", metadata() + header() + content() + footer());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    static final class JsonItem {
        @SerializedName("id")
        private int id;
        @SerializedName("name")
        private String name;
        @SerializedName("displayName")
        private String display;
        @SerializedName("stackSize")
        private int maxStack;

        JsonItem(int id, String name, String display, int maxStack) {
            this.id = id;
            this.name = name;
            this.display = display;
            this.maxStack = maxStack;
        }

        @SerializedName("id")
        public int id() {
            return id;
        }

        @SerializedName("name")
        public String name() {
            return name;
        }

        @SerializedName("displayName")
        public String display() {
            return display;
        }

        @SerializedName("stackSize")
        public int maxStack() {
            return maxStack;
        }
    }

    private String metadata() {
        return """
        // THIS FILE IS GENERATED FROM MINECRAFT DATA, DO NOT EDIT
        // https://github.com/Cosmorise/Passport/generator/src/main/java/me/combimagnetron/passport/generator/ItemGenerator.java
        """;
    }

    private String header() {
        return """
        package me.combimagnetron.generated.item;
        
        import net.kyori.adventure.text.Component;
        import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer;
        import org.jglrxavpok.hephaistos.nbt.NBT;
        import org.jglrxavpok.hephaistos.nbt.NBTCompound;
        import org.jglrxavpok.hephaistos.nbt.mutable.MutableNBTCompound;
        
        import java.util.ArrayList;
        import java.util.List;
        
        public interface Material {
        """;
    }

    private String footer() {
        return """
                    static Material of(int material) {
                        return new Impl(material);
                    }
                   \s
                    record Impl(int material) implements Material {
                       \s
                    }
        }
        """;
    }

    private String content() {
        StringBuilder builder = new StringBuilder();
        for (JsonItem item : items) {
            builder.append("    Material").append(" ").append(item.name().toUpperCase()).append(" = Material.of(").append(item.id).append(");\n");
        }
        return builder.toString();
    }

}
