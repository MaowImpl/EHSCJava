package maow.ehsc.util.xml.wrapper;

import org.dom4j.Attribute;

public class AttributeWrapper {
    private final Attribute attribute;

    private AttributeWrapper(Attribute attribute) {
        this.attribute = attribute;
    }

    public static AttributeWrapper create(Attribute attribute) {
        return new AttributeWrapper(attribute);
    }

    public static AttributeWrapper empty() {
        return new AttributeWrapper(null);
    }

    public String name() {
        if (attribute != null) {
            return attribute.getName();
        }
        return "";
    }

    public String value() {
        if (attribute != null) {
            return attribute.getValue();
        }
        return "";
    }
}
