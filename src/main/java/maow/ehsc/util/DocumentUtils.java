package maow.ehsc.util;

import maow.ehsc.EHSC;
import org.dom4j.Document;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;

public final class DocumentUtils {
    public static final OutputFormat FORMAT_STANDARD = new OutputFormat();

    public static void writeDocument(Document document, Path path) {
        final FileWriter fw;
        XMLWriter xw = null;
        try {
            fw = new FileWriter(concatFilename(path));
            xw = new XMLWriter(fw, FORMAT_STANDARD);

            xw.write(document);
            xw.flush();
            EHSC.log("Document finished converting.");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (xw != null) {
                    xw.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static File concatFilename(Path path) {
        final File file = path.toFile();
        final String full = file.getName();
        final String name = full.substring(0, full.lastIndexOf('.'));
        return new File(name + "_Compiled.xml");
    }

    static {
        FORMAT_STANDARD.setNewlines(true);
        FORMAT_STANDARD.setIndent(true);
        FORMAT_STANDARD.setPadText(true);
        FORMAT_STANDARD.setTrimText(false);
    }
}
