package maow.ehsc.xml.modifier;

import maow.ehsc.xml.spec.AttributeSpec;
import maow.ehsc.xml.spec.ElementSpec;
import org.dom4j.Attribute;
import org.dom4j.Element;

public final class AttributeModifier extends Modifier<Attribute> {
    private final Attribute attribute;

    public AttributeModifier(Element parent, Element modified, Attribute attribute) {
        super(parent, modified, attribute);
        this.attribute = attribute;
    }

    public String name() {
        return attribute.getName();
    }

    public String value() {
        return attribute.getValue();
    }

    @Deprecated
    @Override
    public void convert(AttributeSpec spec1, ElementSpec spec2) {}
}
