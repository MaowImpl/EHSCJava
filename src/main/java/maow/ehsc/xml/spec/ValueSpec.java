package maow.ehsc.xml.spec;

public final class ValueSpec implements Spec<Void> {
    private final String type;
    private final String value;

    public ValueSpec(String type, String value) {
        this.type = type;
        this.value = value;
    }

    public ValueSpec(String type) {
        this.type = type;
        this.value = "";
    }

    public String type() {
        return type;
    }

    public String value() {
        return value;
    }

    @Deprecated
    @Override
    public Spec<Void> with(ElementSpec spec)  {
        return null;
    }

    @Deprecated
    @Override
    public Spec<Void> with(AttributeSpec spec) {
        return null;
    }

    @Deprecated
    @Override
    public Spec<Void> with(ValueSpec spec) {
        return null;
    }

    @Deprecated
    @Override
    public Void build() {
        return null;
    }
}