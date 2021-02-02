package maow.ehsc.xml.handler;

import maow.ehsc.xml.modifier.AttributeModifier;
import maow.ehsc.xml.modifier.ElementModifier;
import maow.ehsc.xml.spec.AttributeSpec;
import maow.ehsc.xml.spec.ElementSpec;
import maow.ehsc.xml.spec.ValueSpec;
import org.dom4j.Attribute;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.util.List;
import java.util.function.Consumer;

public final class ElementHandler {
    private final Element original;
    private Element modified;

    private ElementHandler(Element element) {
        this.original = element;
        this.modified = DocumentHelper.createElement("NULL");
    }

    public static ElementHandler create(Element element) {
        return new ElementHandler(element);
    }

    public static ElementHandler empty() {
        return new ElementHandler(null);
    }

    public ElementHandler convert(ElementSpec spec, Consumer<ElementModifier> consumer) {
        final String name = spec.name();
        final List<Element> elements = original.elements(name);
        for (Element element : elements) {
            final ElementModifier modifier = new ElementModifier(original, modified, element);
            consumer.accept(modifier);
            modified = modifier.build();
        }
        return this;
    }

    public ElementHandler convert(AttributeSpec spec, Consumer<AttributeModifier> consumer) {
        final String name = spec.name();
        final Attribute attribute = original.attribute(name);
        if (attribute != null) {
            final AttributeModifier modifier = new AttributeModifier(original, modified, attribute);
            consumer.accept(modifier);
            modified = modifier.build();
        }
        return this;
    }

    public ElementHandler add(ElementSpec... specs) {
        for (ElementSpec spec : specs) {
            final Element element = spec.build();
            modified.add(element);
        }
        return this;
    }

    public ElementHandler add(AttributeSpec... specs) {
        for (AttributeSpec spec : specs) {
            final Attribute attribute = spec.build();
            modified.add(attribute);
        }
        return this;
    }

    public ElementHandler add(ValueSpec... specs) {
        for (ValueSpec spec : specs) {
            final String type = spec.type();
            final String value = spec.value();
            switch (type.toLowerCase()) {
                case "name":
                    modified.setName(value);
                    break;
                case "text":
                    modified.setText(value);
                    break;
            }
        }
        return this;
    }

    public ElementHandler skip(ElementSpec... specs) {
        for (ElementSpec spec : specs) {
            final String name = spec.name();
            final List<Element> elements = original.elements(name);
            for (Element element : elements) {
                element.setParent(null);
                modified.add(element);
            }
        }
        return this;
    }

    public ElementHandler skip(AttributeSpec... specs) {
        for (AttributeSpec spec : specs) {
            final String name = spec.name();
            final Attribute attribute = original.attribute(name);
            if (attribute != null) {
                attribute.setParent(null);
                modified.add(attribute);
            }
        }
        return this;
    }

    public ElementHandler skip(ValueSpec... specs) {
        for (ValueSpec spec : specs) {
            final String type = spec.type();
            switch (type) {
                case "name":
                    final String name = original.getName();
                    modified.setName(name);
                    break;
                case "text":
                    final String text = original.getText();
                    modified.setText(text);
                    break;
            }
        }
        return this;
    }

    public ElementHandler translate(ElementSpec spec1, ElementSpec spec2) {
        final String name1 = spec1.name();
        final String name2 = spec2.name();
        final List<Element> elements = original.elements(name1);
        for (Element element : elements) {
            element.setParent(null);
            element.setName(name2);
            modified.add(element);
        }
        return this;
    }

    public ElementHandler translate(AttributeSpec spec1, AttributeSpec spec2) {
        final String name1 = spec1.name();
        final String name2 = spec2.name();
        final Attribute attribute = original.attribute(name1);
        if (attribute != null) {
            final String value = attribute.getValue();
            modified.addAttribute(name2, value);
        }
        return this;
    }

    public ElementHandler translate(AttributeSpec spec1, ElementSpec spec2) {
        final String name1 = spec1.name();
        final String name2 = spec2.name();
        final Attribute attribute = original.attribute(name1);
        if (attribute != null) {
            final String value = attribute.getValue();
            final Element element = modified.addElement(name2);
            element.setText(value);
        }
        return this;
    }

    public ElementHandler translate(ElementSpec spec1, AttributeSpec spec2) {
        final String name1 = spec1.name();
        final String name2 = spec2.name();
        final List<Element> elements = original.elements(name1);
        for (Element element : elements) {
            final String text = element.getText();
            modified.addAttribute(name2, text);
        }
        return this;
    }

    public Element build() {
        return modified;
    }
}