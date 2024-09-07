package me.combimagnetron.passport.util.file;

import java.nio.file.Path;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Folder implements File<Collection<File<?>>> {
    private final Map<String, File<?>> files = new HashMap<>();
    private final String name;

    Folder(String name, File<?>... files) {
        this.name = name;
        content(List.of(files));
    }

    @Override
    public void save(Path path) {
        path.toFile().mkdirs();
        files.values().forEach(file -> file.save(path));
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public Collection<File<?>> content() {
        return files.values();
    }

    @Override
    public void content(Collection<File<?>> content) {
        this.files.clear();
        content.forEach(file -> files.put(adapt(file.name()), file));
    }

    public void add(File<?> file) {
        this.files.put(adapt(file.name()), file);
    }

    public void remove(String name) {
        this.files.remove(adapt(name));
    }

    private String adapt(String fileName) {
        return fileName.split("\\.")[0];
    }

    public static Folder of(String name, File<?>... files) {
        return new Folder(name, files);
    }

    public static Folder empty(String name) {
        return of(name);
    }

}
