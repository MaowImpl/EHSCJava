package maow.ehsc.xml.convert.handler;

import maow.ehsc.EHSC;
import org.dom4j.Attribute;
import org.dom4j.Element;
import org.dom4j.tree.DefaultElement;

import java.nio.file.Path;
import java.util.List;
import java.util.function.BiConsumer;

public class ElementHandler {
    private final Element original;
    private final Element modified;

    private ElementHandler(Element element) {
        this.original = element;
        this.modified = new DefaultElement("Element");
    }

    public static ElementHandler create(Element element) {
        return new ElementHandler(element);
    }

    public static ElementHandler empty() {
        return new ElementHandler(null);
    }

    public ElementHandler addElement(Element element) {
        modified.add(element);
        EHSC.log("Added new element \"" + element.getName() + "\" to element.");
        return this;
    }

    public ElementHandler addAttribute(String name, String value) {
        modified.addAttribute(name, value);
        EHSC.log("Added new attribute \"" + name + "\" to element with value \"" + value + "\"");
        return this;
    }

    public ElementHandler setName(String name) {
        modified.setName(name);
        EHSC.log("Set element name to: \"" + name + "\"");
        return this;
    }

    public ElementHandler setText(String text) {
        modified.setText(text);
        EHSC.log("Set element text to \"" + text + "\"");
        return this;
    }

    public ElementHandler convertElement(String name, BiConsumer<ElementHandler, Element> biConsumer) {
        if (original != null) {
            final Element element = original.element(name);
            if (element != null) {
                final ElementHandler handler = create(element);
                biConsumer.accept(handler, modified);
                EHSC.log("Converted element: \"" + name + "\"");
            }
        }
        return this;
    }

    public ElementHandler convertAttribute(String name, BiConsumer<AttributeHandler, Element> biConsumer) {
        if (original != null) {
            final Attribute attribute = original.attribute(name);
            if (attribute != null) {
                final AttributeHandler handler = AttributeHandler.create(attribute);
                biConsumer.accept(handler, modified);
                EHSC.log("Converted attribute: \"" + name + "\"");
            }
        }
        return this;
    }

    public ElementHandler convertName(BiConsumer<String, Element> biConsumer) {
        if (original != null) {
            final String name = original.getName();
            biConsumer.accept(name, modified);
        }
        return this;
    }

    public ElementHandler convertText(BiConsumer<String, Element> biConsumer) {
        if (original != null) {
            final String text = original.getText();
            biConsumer.accept(text, modified);
        }
        return this;
    }

    public ElementHandler passElement(String name) {
        if (original != null) {
            final Element element = original.element(name);
            if (element != null) {
                element.setParent(null);
                modified.add(element);
                EHSC.log("Made no changes to element: \"" + element.getName() + "\"");
            }
        }
        return this;
    }

    public ElementHandler passElements(String name) {
        if (original != null) {
            final List<Element> elements = original.elements(name);
            for (Element element : elements) {
                element.setParent(null);
                modified.add(element);
                EHSC.log("Made no changes to element: \"" + element.getName() + "\"");
            }
        }
        return this;
    }

    public ElementHandler passAttribute(String name) {
        if (original != null) {
            final Attribute attribute = original.attribute(name);
            if (attribute != null) {
                attribute.setParent(null);
                modified.add(attribute);
                EHSC.log("Made no changes to attribute: \"" + attribute.getName() + "\"");
            }
        }
        return this;
    }

    public ElementHandler passName() {
        if (original != null) {
            final String name = original.getName();
            modified.setName(name);
            EHSC.log("Set element name to: \"" + name + "\"");
        }
        return this;
    }

    public ElementHandler passText() {
        if (original != null) {
            final String text = original.getText();
            modified.setText(text);
            EHSC.log("Set element text to \"" + text + "\"");
        }
        return this;
    }

    public String getName() {
        if (original != null) {
            return original.getName();
        }
        return "";
    }

    public String getText(boolean trim) {
        if (original != null) {
            return (trim) ? original.getTextTrim() : original.getText();
        }
        return "";
    }

    public Element getOriginal() {
        return original;
    }

    public Element build() {
        return modified;
    }
}
