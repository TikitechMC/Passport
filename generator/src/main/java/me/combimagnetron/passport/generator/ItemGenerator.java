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

    public ItemGenerator(File file, String version) {
        try {
            items = new Gson().fromJson(new FileReader(file), new TypeToken<ArrayList<JsonItem>>() {}.getType());
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        //System.out.println(metadata() + header() + content() + "}");
        try {
            Util.write("api/src/main/java/me/combimagnetron/generated/R" + version.replaceAll("\\.", "_") + "/item/Material.java", metadata() + header(version) + content() + footer());
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

    private String header(String version) {
        String firstLine = "package me.combimagnetron.generated.R" + version.replaceAll("\\.", "_") + ".item;\n";
        return firstLine + """

        import me.combimagnetron.passport.internal.registry.Registry;
        import me.combimagnetron.passport.data.Identifier;

        import java.util.ArrayList;
        import java.util.List;
        import java.util.Map;
        
        public interface Material {
            Registry<Material> REGISTRY = Registry.empty();
        """;
    }

    private String footer() {
        return """
          \s
           int material();
          \s
           Identifier identifier();
          \s
           static Material of(int id, Identifier identifier) {
               Material material = new Impl(id, identifier);
               REGISTRY.register(identifier, material);
               return material;
           }
              \s
           static Material material(Identifier identifier) {
               return REGISTRY.get(identifier);
           }
                          \s
           static Material direct(int id) {
               return ((Registry.Impl<Material>) REGISTRY).registry().values().stream().filter(material -> material.material() == id).findFirst().orElseThrow();
           }
             \s
           record Impl(int material, Identifier identifier) implements Material {
                  \s
           }
       }
       \s""";
    }

    private String content() {
        StringBuilder builder = new StringBuilder();
        for (JsonItem item : items) {
            builder.append("    Material").append(" ").append(item.name().toUpperCase()).append(" = Material.of(").append(item.id).append(", Identifier.of(\"minecraft\", \"").append(item.name).append("\"));\n");
        }
        return builder.toString();
    }

}
