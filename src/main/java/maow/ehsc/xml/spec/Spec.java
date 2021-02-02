package maow.ehsc.xml.spec;

public interface Spec<T> {
    Spec<T> with(ElementSpec spec);
    Spec<T> with(AttributeSpec spec);
    Spec<T> with(ValueSpec spec);
    T build();
}
