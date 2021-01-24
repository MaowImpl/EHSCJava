package maow.ehsc.xml.convert;

import maow.ehsc.xml.convert.handler.AttributeHandler;
import maow.ehsc.xml.convert.handler.ElementHandler;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

public abstract class AbstractDocumentConverter {
    private final Document document;

    protected AbstractDocumentConverter(Document document) {
        this.document = document;
    }

    public Document convert() {
        final Element element = doConversion();
        final Document converted = DocumentHelper.createDocument();
        converted.add(element);
        return converted;
    }

    protected ElementHandler root() {
        final Element root = document.getRootElement();
        return (root != null) ? ElementHandler.create(root) : ElementHandler.empty();
    }

    protected abstract Element doConversion();

    protected static class Utils {
        public static void addElement(Element parent, String name, String text) {
            final Element element = parent.addElement(name);
            element.setText(text);
        }

        public static void addElement(Element parent, String name, AttributeHandler attributeHandler) {
            final String value = attributeHandler.getValue();
            addElement(parent, name, value);
        }

        public static void addElement(Element parent, String name, ElementHandler elementHandler) {
            final String text = elementHandler.getText(true);
            addElement(parent, name, text);
        }

        public static boolean hasElement(ElementHandler handler, String name) {
            final Element original = handler.getOriginal();
            if (original != null) {
                return original.getParent().element(name) != null;
            }
            return false;
        }

        public static boolean hasAttribute(Element element, String name) {
            if (element != null) {
                return element.attribute(name) != null;
            }
            return false;
        }
    }
}
