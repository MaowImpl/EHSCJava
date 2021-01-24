package maow.ehsc.xml.convert.handler;

import org.dom4j.Attribute;
import org.dom4j.tree.DefaultAttribute;

import java.util.function.Function;

public class AttributeHandler {
    private final Attribute original;
    private final Attribute modified;

    private AttributeHandler(Attribute attribute) {
        this.original = attribute;
        this.modified = new DefaultAttribute("attribute", "");
    }

    public static AttributeHandler create(Attribute attribute) {
        return new AttributeHandler(attribute);
    }

    public static AttributeHandler empty() {
        return new AttributeHandler(null);
    }

    public AttributeHandler setValue(String value) {
        modified.setValue(value);
        return this;
    }

    public AttributeHandler setName(String name) {
        modified.setName(name);
        return this;
    }

    public AttributeHandler convertValue(Function<String, String> function) {
        if (original != null) {
            final String value = original.getValue();
            final String newValue = function.apply(value);
            modified.setValue(newValue);
        }
        return this;
    }

    public AttributeHandler convertName(Function<String, String> function) {
        if (original != null) {
            final String name = original.getName();
            final String newName = function.apply(name);
            modified.setName(newName);
        }
        return this;
    }

    public AttributeHandler passValue() {
        if (original != null) {
            final String value = original.getValue();
            modified.setValue(value);
        }
        return this;
    }

    public AttributeHandler passName() {
        if (original != null) {
            final String name = original.getName();
            modified.setName(name);
        }
        return this;
    }

    public String getValue() {
        return (original != null) ? original.getValue() : "";
    }

    public String getName() {
        return (original != null) ? original.getName() : "";
    }

    public Attribute build() {
        return modified;
    }
}
