package maow.ehsc.xml;

import org.dom4j.Attribute;
import org.dom4j.Element;
import org.dom4j.tree.DefaultElement;

public final class ElementBuilder {
    private String name;
    private String text = "";
    private Attribute[] attributes = new Attribute[0];
    private Element[] elements = new Element[0];

    public ElementBuilder(String name) {
        this.name = name;
    }

    public ElementBuilder name(String name) {
        this.name = name;
        return this;
    }

    public ElementBuilder text(String text) {
        this.text = text;
        return this;
    }

    public ElementBuilder attributes(Attribute... attributes) {
        this.attributes = attributes;
        return this;
    }

    public ElementBuilder elements(Element... elements) {
        this.elements = elements;
        return this;
    }

    public Element build() {
        final Element e = new DefaultElement(name);
        e.setText(text);
        for (Attribute attribute : attributes) e.add(attribute);
        for (Element element : elements) e.add(element);
        return e;
    }
}
