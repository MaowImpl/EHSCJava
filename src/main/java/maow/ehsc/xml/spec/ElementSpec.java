package maow.ehsc.xml.spec;

import org.dom4j.Attribute;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.util.HashSet;
import java.util.Set;

public final class ElementSpec implements Spec<Element> {
    private String name;
    private String text;
    private final Set<AttributeSpec> attributes = new HashSet<>();
    private final Set<ElementSpec> elements = new HashSet<>();

    public ElementSpec(String name, String text) {
        this.name = name;
        this.text = text;
    }

    public ElementSpec(String name) {
        this.name = name;
    }

    public ElementSpec() {}

    public String name() {
        return name;
    }

    public String text() {
        return text;
    }

    public Set<AttributeSpec> attributes() {
        return attributes;
    }

    public Set<ElementSpec> elements() {
        return elements;
    }

    @Override
    public ElementSpec with(ElementSpec spec) {
        elements.add(spec);
        return this;
    }

    @Override
    public ElementSpec with(AttributeSpec spec) {
        attributes.add(spec);
        return this;
    }

    @Override
    public ElementSpec with(ValueSpec spec) {
        final String type = spec.type();
        switch (type.toLowerCase()) {
            case "name":
                name = spec.value();
                break;
            case "text":
                text = spec.value();
                break;
        }
        return this;
    }

    @Override
    public Element build() {
        final Element element = DocumentHelper.createElement(name);
        for (AttributeSpec spec : attributes) {
            final Attribute attribute = spec.build();
            element.add(attribute);
        }
        if (text != null) {
            element.setText(text);
        } else {
            for (ElementSpec spec : elements) {
                final Element child = spec.build();
                element.add(child);
            }
        }
        return element;
    }
}