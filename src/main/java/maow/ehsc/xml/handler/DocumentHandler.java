package maow.ehsc.xml.handler;

import maow.ehsc.xml.spec.ValueSpec;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

public final class DocumentHandler {
    private final Document original;
    private final Document modified;

    private DocumentHandler(Document document) {
        this.original = document;
        this.modified = DocumentHelper.createDocument();
    }

    public static DocumentHandler create(Document document) {
        return new DocumentHandler(document);
    }

    public static DocumentHandler empty() {
        return new DocumentHandler(null);
    }

    public ElementHandler root() {
        final Element element = original.getRootElement();
        final ElementHandler handler = ElementHandler.create(element);
        handler.skip(new ValueSpec("name"));
        return handler;
    }

    public ElementHandler root(String name) {
        final Element element = original.getRootElement();
        final ElementHandler handler = ElementHandler.create(element);
        handler.add(new ValueSpec("name", name));
        return handler;
    }

    public Document build() {
        return modified;
    }
}