package maow.ehsc;

import joptsimple.OptionParser;
import joptsimple.OptionSet;
import joptsimple.OptionSpec;
import maow.ehsc.util.xml.wrapper.ElementWrapper;
import maow.ehsc.util.xml.TypeRegistry;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class EHSC {
    public static void main(String[] args) throws IOException {
        handleArguments(args);
    }

    private static void handleArguments(String[] args) throws IOException {
        final OptionParser op = new OptionParser();
        final OptionSpec<String> fileSpec = op.accepts("f", "Select a directory or file to compile.").withRequiredArg().required();
        final OptionSpec<Void> helpSpec = op.accepts("h", "View help and information about this program and its arguments.").forHelp();

        final OptionSet options = op.parse(args);

        if (options.has(helpSpec)) {
            op.printHelpOn(System.out);
        }

        if (options.has(fileSpec)) {
            final String value = options.valueOf(fileSpec);
            final Path path = Paths.get(value);
            if (Files.notExists(path)) {
                try {
                    throw new FileNotFoundException("Target file/directory specified in '-f' does not exist.");
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
            log("Selected path: " + path);
            convert(path);
        }
    }

    private static void convert(Path path) {
        log("Attempting to convert file.");
        final SAXReader sr = new SAXReader();
        try {
            final Document document = sr.read(path.toFile());
            final ElementWrapper root = ElementWrapper.root(document);
            TypeRegistry.processType(root, path);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }

    private static void log(String msg) {
        final String full = "[EHSC] " + msg;
        System.out.println(full);
    }
}