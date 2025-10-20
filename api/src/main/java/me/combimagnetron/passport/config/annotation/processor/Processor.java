package me.combimagnetron.passport.config.annotation.processor;

import com.typesafe.config.ConfigFactory;
import com.typesafe.config.ConfigObject;
import com.typesafe.config.ConfigParseOptions;
import me.combimagnetron.passport.config.Config;
import me.combimagnetron.passport.config.annotation.*;
import me.combimagnetron.passport.config.annotation.Optional;
import me.combimagnetron.passport.config.element.Node;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

public class Processor {

    public static <T> T load(Class<T> clazz, Path path) {
        final Map<Class<?>, Object> arguments = new LinkedHashMap<>();
        final Config template = transform(clazz);
        final Set<Class<?>> fields = Arrays.stream(clazz.getEnclosingConstructor().getParameterTypes()).collect(Collectors.toSet());
        final Set<Object> objects = new HashSet<>();
        final com.typesafe.config.Config config = ConfigFactory.parseFile(path.toFile());
        for (Node<?> node : template.nodes()) {
            Object object = config.getAnyRef(node.name());
            arguments.put(node.type(), object);
        }
        try {
            return clazz.getDeclaredConstructor(arguments.keySet().toArray(new Class[0])).newInstance(arguments.values().toArray());
        } catch (NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public static Config read(Path path) {
        ConfigObject object = ConfigFactory.parseFile(path.toFile()).root();
        Config config = Config.config();
        object.unwrapped().forEach(((string, o) -> {
            if (o instanceof HashMap) {
                config.section(handleSection((HashMap<String, Object>) o, string));
                return;
            }
            config.node(Node.required(string, o));
        }
        ));
        return config;
    }

    private static me.combimagnetron.passport.config.element.Section handleSection(HashMap<String, Object> values, String name) {
        me.combimagnetron.passport.config.element.Section section = me.combimagnetron.passport.config.element.Section.required(name);
        for (Map.Entry<String, Object> entry : values.entrySet()) {
            if (entry.getValue() instanceof HashMap) {
                section.section(handleSection((HashMap<String, Object>) entry.getValue(), entry.getKey()));
            } else {
                Node<Object> node = Node.required(entry.getKey(), entry.getValue());
                section.node(node);
            }
        }
        return section;
    }

    private static me.combimagnetron.passport.config.element.Section processSection(ProcessedField<?> processedField, me.combimagnetron.passport.config.element.Section section) {
        Set<Field> fields = Arrays.stream(processedField.type().getFields()).filter(field -> !field.isAnnotationPresent(Excluded.class)).collect(Collectors.toSet());
        for (Field field : fields) {
            final ProcessedField<?> processedField1 = ProcessedField.from(field);
            if (field.isAnnotationPresent(Section.class)) {
                me.combimagnetron.passport.config.element.Section section1 = me.combimagnetron.passport.config.element.Section.required(processedField1.name());
                processSection(ProcessedField.from(field), section1);
                section.section(section1);
            } else if (field.isAnnotationPresent(Optional.class)) {
                section.node(Node.optional(processedField1.name(), processedField1.type()));
            } else if (field.isAnnotationPresent(Required.class)) {
                section.node(Node.required(processedField1.name(), processedField1.type()));
            } else if (field.isAnnotationPresent(Anonymous.class)) {
                section.node(Node.anonymous(processedField1.type()));
            }
        }
        return section;
    }

    @NotNull
    private static Config transform(Class<?> clazz) {
        Config config = Config.config();
        if (!clazz.isAnnotationPresent(me.combimagnetron.passport.config.annotation.Config.class)) {
            return config;
        }
        Set<Field> fields = Arrays.stream(clazz.getFields()).filter(field -> !field.isAnnotationPresent(Excluded.class)).collect(Collectors.toSet());
        for (Field field : fields) {
            final ProcessedField<?> processedField = ProcessedField.from(field);
            if (field.isAnnotationPresent(Section.class)) {
                me.combimagnetron.passport.config.element.Section section = me.combimagnetron.passport.config.element.Section.required(processedField.name());
                processSection(ProcessedField.from(field), section);
                config.section(section);
                if (field.isAnnotationPresent(Many.class)) {
                    ((me.combimagnetron.passport.config.element.Section.RequiredSection) section).manyFlagged = true;
                }
            } else if (field.isAnnotationPresent(Optional.class)) {
                config.node(Node.optional(processedField.name(), processedField.type()));
            } else if (field.isAnnotationPresent(Required.class)) {
                config.node(Node.required(processedField.name(), processedField.type()));
            } else if (field.isAnnotationPresent(Anonymous.class)) {
                config.node(Node.anonymous(processedField.type()));
            }

        }
        return config;
    }

    record ProcessedField<T>(String name, Class<T> type) {

        public static <V> ProcessedField<V> from(Field field) {
            String name = field.isAnnotationPresent(Name.class) ? field.getAnnotation(Name.class).name() : field.getName();
            Class<V> type = (Class<V>) field.getType();
            return new ProcessedField<>(name, type);
        }

    }

}
