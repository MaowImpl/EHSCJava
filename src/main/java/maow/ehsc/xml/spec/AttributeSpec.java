package maow.ehsc.xml.spec;

import org.dom4j.Attribute;
import org.dom4j.DocumentHelper;

public final class AttributeSpec implements Spec<Attribute> {
    private String name;
    private String value;

    public AttributeSpec(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public AttributeSpec(String name) {
        this.name = name;
    }

    public AttributeSpec() {}

    public String name() {
        return name;
    }

    public String value() {
        return value;
    }

    @Override
    public AttributeSpec with(ElementSpec spec) {
        spec.with(this);
        return this;
    }

    @Deprecated
    @Override
    public AttributeSpec with(AttributeSpec spec) {
        return null;
    }

    @Override
    public AttributeSpec with(ValueSpec spec) {
        final String type = spec.type();
        switch (type.toLowerCase()) {
            case "name":
                name = spec.value();
                break;
            case "text":
                value = spec.value();
                break;
        }
        return this;
    }

    @Override
    public Attribute build() {
        return DocumentHelper.createAttribute(null, name, value);
    }
}
