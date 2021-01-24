package maow.ehsc.xml.convert;

import maow.ehsc.EHSC;
import maow.ehsc.xml.convert.types.*;
import org.dom4j.Document;
import org.dom4j.Element;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

public final class ConverterFactory {
    private static final Map<String, Function<Document, AbstractDocumentConverter>> CREATORS = new HashMap<>();

    public static Optional<Document> convert(Document document) {
        Document converted = null;
        final Element root = document.getRootElement();
        if (root != null) {
            final String name = root.getName();
            EHSC.log("Detected a(n) " + name + " document.");
            final AbstractDocumentConverter converter = CREATORS
                    .get(name)
                    .apply(document);
            converted = converter.convert();
        }
        return Optional.ofNullable(converted);
    }

    private static void add(String name, Function<Document, AbstractDocumentConverter> creator) {
        CREATORS.put(name, creator);
    }

    static {
        add("Extension", ExtensionConverter::new);
        add("Computer", ComputerConverter::new);
        add("Mission", MissionConverter::new);
    }
}
