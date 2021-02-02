package maow.ehsc.xml.converter.types;

import maow.ehsc.xml.converter.AbstractDocumentConverter;
import org.dom4j.Document;
import org.dom4j.Element;

public final class ComputerConverter extends AbstractDocumentConverter {
    public ComputerConverter(Document document) {
        super(document);
    }

    @Override
    protected Element _convert() {
        return doc()
                .root().build();
    }
}