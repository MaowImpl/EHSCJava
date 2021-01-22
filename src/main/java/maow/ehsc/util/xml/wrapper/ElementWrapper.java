package maow.ehsc.util.xml.wrapper;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class ElementWrapper {
    private final Element element;

    private ElementWrapper(Element element) {
        this.element = element;
    }

    public static ElementWrapper root(Document document) {
        final Element root = document.getRootElement();
        if (root != null) {
            return new ElementWrapper(root);
        }
        return ElementWrapper.empty();
    }

    public static ElementWrapper create(Element element) {
        return new ElementWrapper(element);
    }

    public static ElementWrapper empty() {
        return new ElementWrapper(null);
    }

    public ElementWrapper child(String name) {
        if (element != null) {
            final Element child = element.element(name);
            if (child != null) {
                return ElementWrapper.create(child);
            }
        }
        return ElementWrapper.empty();
    }

    public List<ElementWrapper> children(String name) {
        if (element != null) {
            return element.elements(name)
                    .stream()
                    .map(ElementWrapper::create)
                    .collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    public List<ElementWrapper> children() {
        if (element != null) {
            return element.elements()
                    .stream()
                    .map(ElementWrapper::create)
                    .collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    public AttributeWrapper attribute(String name) {
        if (element != null) {
            final Attribute attribute = element.attribute(name);
            return AttributeWrapper.create(attribute);
        }
        return AttributeWrapper.empty();
    }

    public List<AttributeWrapper> attributes() {
        if (element != null) {
            return element.attributes()
                    .stream()
                    .map(AttributeWrapper::create)
                    .collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    public String text(boolean trim) {
        if (element != null) {
            return (trim)
                    ? element.getTextTrim()
                    : element.getText();
        }
        return "";
    }

    public String text() {
        return text(true);
    }

    public String name() {
        return (element != null)
                ? element.getName()
                : "";
    }

    public boolean isEmpty() {
        return element == null;
    }
}
