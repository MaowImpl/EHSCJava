package maow.ehsc.util.xml;

import maow.ehsc.util.xml.wrapper.ElementWrapper;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;

public final class TypeRegistry {
    private static final Map<String, DocumentType<?>> TYPES = new HashMap<>();

    public static void processType(ElementWrapper root, Path path) {
        final String name = root.name();
        final DocumentType<?> type = getType(name);
        if (type != null) type.write(root, path);
    }

    public static <T> void addType(String name, Function<ElementWrapper, T> creator, BiConsumer<T, Path> writer) {
        final DocumentType<T> type = new DocumentType<>(creator, writer);
        TYPES.put(name, type);
    }

    public static DocumentType<?> getType(String name) {
        return TYPES.get(name);
    }

    static {
        addType("Extension", DocumentUtils::createExtension, DocumentUtils::writeExtension);
        addType("Computer", DocumentUtils::createComputer, DocumentUtils::writeComputer);
    }

    private static class DocumentType<T> {
        private final Function<ElementWrapper, T> creator;
        private final BiConsumer<T, Path> writer;

        public DocumentType(Function<ElementWrapper, T> creator, BiConsumer<T, Path> writer) {
            this.creator = creator;
            this.writer = writer;
        }

        public void write(ElementWrapper root, Path path) {
            T t = creator.apply(root);
            writer.accept(t, path);
        }
    }
}
