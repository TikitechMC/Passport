package me.combimagnetron.passport.generator;

import java.io.File;
import java.net.URISyntaxException;

public class Main {

    public static void main(String[] args) {
        try {
            new ItemGenerator(new File(Main.class.getClassLoader().getResource("items.json").toURI()));
            new BlockGenerator(new File(Main.class.getClassLoader().getResource("blocks.json").toURI()));
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

}
