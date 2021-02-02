package maow.ehsc.xml.modifier;

import maow.ehsc.xml.spec.AttributeSpec;
import maow.ehsc.xml.spec.ElementSpec;
import org.dom4j.Attribute;
import org.dom4j.Element;
import org.dom4j.Node;

import java.util.function.Predicate;

public abstract class Modifier<T extends Node> {
    private final Element original;
    private final Element modified;
    private final T t;

    protected boolean result;
    protected boolean inverted;

    public Modifier(Element original, Element modified, T t) {
        this.original = original;
        this.modified = modified;
        this.t = t;
        reset();
    }

    protected boolean test() {
        final boolean test = (result && inverted) || (!result && !inverted);
        reset();
        return test;
    }

    private void reset() {
        result = true;
        inverted = false;
    }

    public void add(ElementSpec spec) {
        if (test()) return;
        final Element element = spec.build();
        modified.add(element);
    }

    public void add(AttributeSpec spec) {
        if (test()) return;
        final Attribute attribute = spec.build();
        modified.add(attribute);
    }

    public Modifier<T> pred(Predicate<T> predicate) {
        result = predicate.test(t);
        return this;
    }

    public Modifier<T> has(ElementSpec spec) {
        final String name = spec.name();
        final Element element = original.element(name);
        result = (element != null);
        return this;
    }

    public Modifier<T> has(AttributeSpec spec) {
        final String name = spec.name();
        final Attribute attribute = original.attribute(name);
        result = (attribute != null);
        return this;
    }

    public Modifier<T> not() {
        inverted = true;
        return this;
    }

    public abstract void convert(AttributeSpec spec1, ElementSpec spec2);

    public Element build() {
        return modified;
    }
}