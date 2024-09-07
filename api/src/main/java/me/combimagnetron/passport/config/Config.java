package me.combimagnetron.passport.config;

import com.typesafe.config.*;
import me.combimagnetron.passport.config.annotation.processor.Processor;
import me.combimagnetron.passport.config.element.ConfigElement;
import me.combimagnetron.passport.config.element.Node;
import me.combimagnetron.passport.config.element.Section;
import me.combimagnetron.passport.config.reader.Reader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public sealed interface Config permits Config.Impl {

    <T> Config node(Node<T> node);

    Config section(Section section);

    Collection<Node<?>> nodes();

    Collection<Section> sections();

    Reader reader();

    void save(Path path);

    static Config config() {
        return new Impl();
    }

    static Config config(Settings settings) {
        return new Impl(settings);
    }

    static Config file(Path path) {
        return Processor.read(path);
    }

    static <T> T map(Class<T> clazz, Path path) {
        return Processor.load(clazz, path);
    }

    final class Impl implements Config {
        private final LinkedHashMap<String, Section> sections = new LinkedHashMap<>();
        private final LinkedHashMap<String, Node<?>> nodes = new LinkedHashMap<>();
        private Settings settings = Settings.settings();

        Impl(Settings settings) {
            this.settings = settings;
        }

        Impl() {

        }

        @Override
        public <T> Config node(Node<T> node) {
            this.nodes.put(node.name(), node);
            return this;
        }

        @Override
        public Config section(Section section) {
            this.sections.put(section.name(), section);
            return this;
        }

        @Override
        public Collection<Node<?>> nodes() {
            return nodes.values();
        }

        @Override
        public Collection<Section> sections() {
            return sections.values();
        }

        @Override
        public Reader reader() {
            return new Reader(sections, nodes);
        }

        @Override
        public void save(Path path) {
            com.typesafe.config.Config config = ConfigFactory.empty();
            final List<Node<?>> nodes = List.copyOf(this.nodes.values());
            for (Node<?> value : nodes) {
                if (check(value)) {
                    continue;
                }
                config = config.withValue(value.name(), ConfigValueFactory.fromAnyRef(value.value()));
            }
            final List<Section> sections = new ArrayList<>(this.sections().stream().toList());
            Collections.reverse(sections);
            for (Section value : sections) {
                final List<ConfigElement> elements = new ArrayList<>(value.elements().stream().toList());
                for (ConfigElement element : elements) {
                    if (!(element instanceof Node<?> node) || check(node)) {
                        continue;
                    }
                    config = config.withValue(value.name() + "." + node.name(), ConfigValueFactory.fromAnyRef(node.value()));
                }
            }
            ConfigRenderOptions options = ConfigRenderOptions.defaults()
                    .setOriginComments(false)
                    .setJson(false);
            String renderedConfig = config.root().render(options);
            try {
                Files.write(path, renderedConfig.getBytes());
            } catch (IOException e) {

            }
        }

        private static boolean check(Node<?> node) {
            return node instanceof Node.OptionalNode<?> || node instanceof Node.AnonymousNode<?>;
        }
    }

}
