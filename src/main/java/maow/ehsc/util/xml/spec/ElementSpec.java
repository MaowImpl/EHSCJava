package maow.ehsc.util.xml.spec;

public class ElementSpec {
    private final String name;
    private final String text;
    private final AttributeSpec[] attributes;

    private ElementSpec(String name, String text, AttributeSpec... attributes) {
        this.name = name;
        this.text = text;
        this.attributes = attributes;
    }

    public static ElementSpec element(String name, String value, AttributeSpec... attributes) {
        return new ElementSpec(name, value, attributes);
    }

    public String getName() {
        return name;
    }

    public String getText() {
        return text;
    }

    public AttributeSpec[] getAttributes() {
        return attributes;
    }
}
