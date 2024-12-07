package me.combimagnetron.passport.generator;

import java.io.File;
import java.net.URISyntaxException;
import java.util.List;

public class Main {
    private final static List<String> VERSIONS = List.of("1.21");

    public static void main(String[] args) {
        for (String version : VERSIONS) {
            try {
                new ItemGenerator(new File(Main.class.getClassLoader().getResource(version + "/items.json").toURI()), version);
                new BlockGenerator(new File(Main.class.getClassLoader().getResource(version + "/blocks.json").toURI()), version);
                new PacketGenerator(new File(Main.class.getClassLoader().getResource(version + "/packets.json").toURI()), version);
            } catch (URISyntaxException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
