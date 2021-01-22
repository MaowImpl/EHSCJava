package maow.ehsc.util.xml.spec;

public class AttributeSpec {
    private final String name;
    private final String value;

    private AttributeSpec(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public static AttributeSpec attribute(String name, String value) {
        return new AttributeSpec(name, value);
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }
}
