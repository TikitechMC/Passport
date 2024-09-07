package me.combimagnetron.passport.config.reader;

import me.combimagnetron.passport.config.element.Node;
import me.combimagnetron.passport.config.element.Section;

import java.util.LinkedHashMap;
import java.util.Map;

public final class Reader {
    private final Map<String, Section> sections;
    private final Map<String, Node<?>> nodes;

    public Reader(LinkedHashMap<String, Section> sections, LinkedHashMap<String, Node<?>> nodes) {
        this.sections = sections;
        this.nodes = nodes;
    }

    public <T> Node<T> node(String name) {
        return (Node<T>) nodes.get(name);
    }

    public Section section(String name) {
        return sections.get(name);
    }

}
