package me.combimagnetron.passport.generator;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;

public class BlockGenerator {
    private final List<JsonBlock> items;

    public BlockGenerator(File file) {
        try {
            items = new Gson().fromJson(new FileReader(file), new TypeToken<ArrayList<JsonBlock>>() {}.getType());
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        //System.out.println(metadata() + header() + content() + "}");
        try {
            Util.write("api/src/main/java/me/combimagnetron/generated/block/Block.java", metadata() + header() + content() + footer());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    static final class State {
        private String name;
        private String type;
        private int num_values;
        private List<Object> values;
    }

    static final class JsonBlock {
        @SerializedName("id")
        private int id;
        @SerializedName("name")
        private String name;
        @SerializedName("displayName")
        private String displayName;
        @SerializedName("hardness")
        private double hardness;
        @SerializedName("resistance")
        private double resistance;
        @SerializedName("stackSize")
        private int stackSize;
        @SerializedName("diggable")
        private boolean diggable;
        @SerializedName("material")
        private String material;
        @SerializedName("transparent")
        private boolean transparent;
        @SerializedName("emitLight")
        private int emitLight;
        @SerializedName("filterLight")
        private int filterLight;
        @SerializedName("defaultState")
        private int defaultState;
        @SerializedName("minStateId")
        private int minStateId;
        @SerializedName("maxStateId")
        private int maxStateId;
        @SerializedName("states")
        private List<State> states;
        @SerializedName("harvestTools")
        private Map<String, Boolean> harvestTools;
        @SerializedName("drops")
        private List<Integer> drops;
        @SerializedName("boundingBox")
        private String boundingBox;

        JsonBlock(int id, String name, String displayName, double hardness, double resistance,
                  int stackSize, boolean diggable, String material, boolean transparent,
                  int emitLight, int filterLight, int defaultState, int minStateId, int maxStateId,
                  List<State> states, Map<String, Boolean> harvestTools, List<Integer> drops,
                  String boundingBox) {
            this.id = id;
            this.name = name;
            this.displayName = displayName;
            this.hardness = hardness;
            this.resistance = resistance;
            this.stackSize = stackSize;
            this.diggable = diggable;
            this.material = material;
            this.transparent = transparent;
            this.emitLight = emitLight;
            this.filterLight = filterLight;
            this.defaultState = defaultState;
            this.minStateId = minStateId;
            this.maxStateId = maxStateId;
            this.states = states;
            this.harvestTools = harvestTools;
            this.drops = drops;
            this.boundingBox = boundingBox;
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
        public String displayName() {
            return displayName;
        }

        @SerializedName("hardness")
        public double hardness() {
            return hardness;
        }

        @SerializedName("resistance")
        public double resistance() {
            return resistance;
        }

        @SerializedName("stackSize")
        public int stackSize() {
            return stackSize;
        }

        @SerializedName("diggable")
        public boolean diggable() {
            return diggable;
        }

        @SerializedName("material")
        public String material() {
            return material;
        }

        @SerializedName("transparent")
        public boolean transparent() {
            return transparent;
        }

        @SerializedName("emitLight")
        public int emitLight() {
            return emitLight;
        }

        @SerializedName("filterLight")
        public int filterLight() {
            return filterLight;
        }

        @SerializedName("defaultState")
        public int defaultState() {
            return defaultState;
        }

        @SerializedName("minStateId")
        public int minStateId() {
            return minStateId;
        }

        @SerializedName("maxStateId")
        public int maxStateId() {
            return maxStateId;
        }

        @SerializedName("states")
        public List<State> states() {
            return states;
        }

        @SerializedName("harvestTools")
        public Map<String, Boolean> harvestTools() {
            return harvestTools;
        }

        @SerializedName("drops")
        public List<Integer> drops() {
            return drops;
        }

        @SerializedName("boundingBox")
        public String boundingBox() {
            return boundingBox;
        }
    }

    private String metadata() {
        return """
        // THIS FILE IS GENERATED FROM MINECRAFT DATA, DO NOT EDIT
        // https://github.com/Cosmorise/Passport/generator/src/main/java/me/combimagnetron/passport/generator/BlockGenerator.java
        """;
    }

    private String header() {
        return """
        package me.combimagnetron.generated.block;

        import java.util.ArrayList;
        import java.util.List;
        import java.util.Map;
        
        public interface Block {
        """;
    }

    private String footer() {
        return """
                    static Block of(int id, String name, String displayName, double hardness, double resistance,
                                    int stackSize, boolean diggable, String boundingBox, boolean transparent,
                                    int emitLight, int filterLight, int defaultState, int minStateId, int maxStateId,
                                    Map<String, Boolean> harvestTools, List<Integer> drops, String type) {
                        return new Impl(id, name, displayName, hardness, resistance, stackSize, diggable, transparent,
                                        emitLight, filterLight, defaultState, minStateId, maxStateId, harvestTools, drops, boundingBox);
                    }
                
                    final class Impl implements Block {
                        private int id;
                        private String name;
                        private String displayName;
                        private double hardness;
                        private double resistance;
                        private int stackSize;
                        private boolean diggable;
                        private boolean transparent;
                        private int emitLight;
                        private int filterLight;
                        private int defaultState;
                        private int minStateId;
                        private int maxStateId;
                        private Map<String, Boolean> harvestTools;
                        private List<Integer> drops;
                        private String boundingBox;
                
                        Impl(int id, String name, String displayName, double hardness, double resistance,
                                  int stackSize, boolean diggable, boolean transparent,
                                  int emitLight, int filterLight, int defaultState, int minStateId, int maxStateId,
                                  Map<String, Boolean> harvestTools, List<Integer> drops,
                                  String boundingBox) {
                            this.id = id;
                            this.name = name;
                            this.displayName = displayName;
                            this.hardness = hardness;
                            this.resistance = resistance;
                            this.stackSize = stackSize;
                            this.diggable = diggable;
                            this.transparent = transparent;
                            this.emitLight = emitLight;
                            this.filterLight = filterLight;
                            this.defaultState = defaultState;
                            this.minStateId = minStateId;
                            this.maxStateId = maxStateId;
                            this.harvestTools = harvestTools;
                            this.drops = drops;
                            this.boundingBox = boundingBox;
                        }
                
                        public int id() {
                            return id;
                        }
                
                        public String name() {
                            return name;
                        }
                
                        public String displayName() {
                            return displayName;
                        }
                
                        public double hardness() {
                            return hardness;
                        }
                
                        public double resistance() {
                            return resistance;
                        }
                
                        public int stackSize() {
                            return stackSize;
                        }
                
                        public boolean diggable() {
                            return diggable;
                        }
                
                        public boolean transparent() {
                            return transparent;
                        }
                
                        public int emitLight() {
                            return emitLight;
                        }
                
                        public int filterLight() {
                            return filterLight;
                        }
                
                        public int defaultState() {
                            return defaultState;
                        }
                
                        public int minStateId() {
                            return minStateId;
                        }
                
                        public int maxStateId() {
                            return maxStateId;
                        }
                
                        public Map<String, Boolean> harvestTools() {
                            return harvestTools;
                        }
                
                        public List<Integer> drops() {
                            return drops;
                        }
                
                        public String boundingBox() {
                            return boundingBox;
                        }
                    }
        }
        """;
    }

    private String content() {
        StringBuilder builder = new StringBuilder();
        for (JsonBlock item : items) {
            builder.append("    Block").append(" ").append(item.name().toUpperCase()).append(" = Block.of(").append(item.id).append(", \"").append(item.name).append("\", \"").append(item.displayName)
                    .append("\", ").append(item.hardness).append(", ").append(item.resistance).append(", ").append(item.stackSize)
                    .append(", ").append(item.diggable).append(", \"").append(item.material).append("\", ").append(item.transparent)
                    .append(", ").append(item.emitLight).append(", ").append(item.filterLight).append(", ").append(item.defaultState)
                    .append(", ").append(item.minStateId).append(", ").append(item.maxStateId)
                    .append(", ").append(adapt(item.harvestTools)).append(", ").append("List.of(").append(item.drops.toString().replace("[", "").replace("]", "")).append("), \"").append(item.boundingBox).append("\"").append(");\n");

        }
        return builder.toString().replaceAll("Map.o\\)", "Map.of()");

    }

    private static String adapt(Map<String, Boolean> map) {
        if (map == null) {
            return "Map.of()";
        }
        StringBuilder builder = new StringBuilder();
        builder.append("Map.of(");
        for (Map.Entry<String, Boolean> entry : map.entrySet()) {
            builder.append("\"").append(entry.getKey()).append("\", ").append(entry.getValue()).append(", ");
        }
        return builder.toString().substring(0, builder.length() - 2) + ")";
    }

}
