package maow.ehsc.xml.converter;

import maow.ehsc.xml.handler.DocumentHandler;
import maow.ehsc.xml.handler.ElementHandler;
import maow.ehsc.xml.spec.AttributeSpec;
import maow.ehsc.xml.spec.ElementSpec;
import maow.ehsc.xml.spec.ValueSpec;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

public abstract class AbstractDocumentConverter {
    private final Document document;

    protected AbstractDocumentConverter(Document document) {
        this.document = document;
    }

    protected DocumentHandler doc() {
        return DocumentHandler.create(document);
    }

    protected ElementHandler root(String name) {
        return doc().root(name);
    }

    protected ElementHandler root() {
        return doc().root();
    }

    protected ElementSpec element(String name, String text) {
        return new ElementSpec(name, text);
    }

    protected ElementSpec element(String name) {
        return new ElementSpec(name);
    }

    protected ElementSpec element() {
        return new ElementSpec();
    }

    protected AttributeSpec attribute(String name, String value) {
        return new AttributeSpec(name, value);
    }

    protected AttributeSpec attribute(String name) {
        return new AttributeSpec(name);
    }

    protected AttributeSpec attribute() {
        return new AttributeSpec();
    }

    protected ValueSpec name(String name) {
        return new ValueSpec("name", name);
    }

    protected ValueSpec name() {
        return new ValueSpec("name");
    }

    protected ValueSpec text(String text) {
        return new ValueSpec("text", text);
    }

    protected ValueSpec text() {
        return new ValueSpec("text");
    }

    public Document convert() {
        final Element element = _convert();
        return DocumentHelper.createDocument(element);
    }

    protected abstract Element _convert();
}
