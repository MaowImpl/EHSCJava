package maow.ehsc.xml.modifier;

import maow.ehsc.xml.spec.AttributeSpec;
import maow.ehsc.xml.spec.ElementSpec;
import org.dom4j.Attribute;
import org.dom4j.Element;

public final class ElementModifier extends Modifier<Element> {
    private final Element parent;
    private final Element modified;
    private final Element element;

    public ElementModifier(Element original, Element modified, Element element) {
        super(original, modified, element);
        this.parent = original;
        this.modified = modified;
        this.element = element;
    }

    @Override
    public void convert(AttributeSpec spec1, ElementSpec spec2) {
        if (test()) return;
        final String name1 = spec1.name();
        final String name2 = spec2.name();
        final Attribute attribute = element.attribute(name1);
        if (attribute != null) {
            final String value = attribute.getValue();
            final Element element = modified.addElement(name2);
            element.setText(value);
        }
    }

    public String text(boolean trim) {
        return (trim) ? element.getTextTrim() : element.getText();
    }

    public String text() {
        return text(true);
    }

    public String attribute(String name) {
        return element.attributeValue(name);
    }

    public ElementModifier element(String name) {
        final Element element = parent.element(name);
        return new ElementModifier(parent, modified, element);
    }
}