package maow.ehsc.util.xml;

import maow.ehsc.util.Language;
import maow.ehsc.util.xml.spec.AttributeSpec;
import maow.ehsc.util.xml.spec.ElementSpec;
import maow.ehsc.util.xml.wrapper.ElementWrapper;
import maow.ehsc.xml.model.Computer;
import maow.ehsc.xml.model.Extension;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

import static maow.ehsc.util.xml.spec.ElementSpec.*;

public final class DocumentUtils {
    public static final OutputFormat STANDARD = new OutputFormat();

    // ========================= //

    // ==== START EXTENSION ==== //

    public static Extension createExtension(ElementWrapper root) {
        final String name = root.attribute("name").value();
        final String lang = root.attribute("lang").value();
        final String allowSaves = root.attribute("allowSaves").value();
        final String intro = root.attribute("intro").value();

        final String startingNodes = root.child("StartingNodes").text();
        final String startingMission = root.child("StartingMission").text();
        final String startingActions = root.child("StartingActions").text();
        final String startingMusic = root.child("StartingMusic").text();
        final String startingTheme = root.child("StartingTheme").text();
        final String description = root.child("Description").text(false);
        final String workshopDescription = root.child("WorkshopDescription").text(false);

        final List<String> factions = root.children("Faction")
                .stream()
                .map(ElementWrapper::text)
                .collect(Collectors.toList());

        final ElementWrapper sequencerElement = root.child("Sequencer");
        final String sequencerTarget = sequencerElement.attribute("target").value();
        final String sequencerSpinupTime = sequencerElement.attribute("spinupTime").value();
        final String sequencerFlag = sequencerElement.attribute("flag").value();
        final String sequencerActions = sequencerElement.attribute("actions").value();
        final Extension.Sequencer sequencer = new Extension.Sequencer(sequencerTarget, sequencerSpinupTime, sequencerFlag, sequencerActions);

        final ElementWrapper workshopElement = root.child("WorkshopInfo");
        final String workshopVisibility = workshopElement.attribute("visibility").value();
        final String workshopTags = workshopElement.attribute("tags").value();
        final String workshopLogo = workshopElement.attribute("logo").value();
        final String workshopId = workshopElement.attribute("id").value();
        final Extension.WorkshopInfo workshopInfo = new Extension.WorkshopInfo(workshopVisibility, workshopTags, workshopLogo, workshopId);

        return new Extension(name, lang, allowSaves, intro, startingNodes, startingMission, startingActions, startingMusic, startingTheme, description, workshopDescription, factions, sequencer, workshopInfo);
    }

    public static void writeExtension(Extension extension, Path path) {
        final Document document = DocumentHelper.createDocument();
        final Element root = document.addElement("HacknetExtension");
        final Language language = Language.fromName(extension.getLang());
        final String workshopDescription = (extension.getWorkshopDescription().isEmpty()) ? extension.getDescription() : extension.getWorkshopDescription();
        final String workshopPublishID = (extension.getWorkshopInfo().getId().isEmpty()) ? "NONE" : extension.getWorkshopInfo().getId();

        addChildren(root,
                element("Language", language.getName()),
                element("WorkshopLanguage", language.getId()),
                element("Name", extension.getName()),
                element("AllowSaves", extension.getAllowSaves()),
                element("StartingVisibleNodes", extension.getStartingNodes()),
                element("StartingMission", extension.getStartingMission()),
                element("StartingActions", extension.getStartingActions()),
                element("StartingFaction", extension.getStartingTheme()),
                element("Description", extension.getDescription()),
                element("WorkshopDescription", workshopDescription),
                element("StartsWithTutorial", "false"),
                element("HasIntroStartup", extension.getIntro()),
                element("IntroStartupSong", extension.getStartingMusic()),
                element("SequencerTargetID", extension.getSequencer().getTarget()),
                element("SequencerSpinUpTime", extension.getSequencer().getSpinupTime()),
                element("SequencerFlagRequiredForStart", extension.getSequencer().getFlag()),
                element("ActionsToRunOnSequencerStart", extension.getSequencer().getActions()),
                element("WorkshopVisibility", extension.getWorkshopInfo().getVisibility()),
                element("WorkshopTags", extension.getWorkshopInfo().getTags()),
                element("WorkshopPreviewImagePath", extension.getWorkshopInfo().getLogo()),
                element("WorkshopPublishID", workshopPublishID)
        );

        for (String faction : extension.getFactions()) {
            addChild(root,
                    element("Faction", faction)
            );
        }

        writeDocument(document, path);
    }

    // ==== START COMPUTER ==== //

    public static Computer createComputer(ElementWrapper root) {
        return null;
    }

    public static void writeComputer(Computer computer, Path path) {

    }

    // ========================= //

    private static void addChildren(Element parent, ElementSpec... children) {
        for (ElementSpec child : children) {
            addChild(parent, child);
        }
    }

    private static void addChild(Element parent, ElementSpec child) {
        if (!child.getText().isEmpty()) {
            final String name = child.getName();
            final String text = child.getText();
            final Element element = parent.addElement(name);
            element.setText(text);
            for (AttributeSpec attribute : child.getAttributes()) {
                final String attributeName = attribute.getName();
                final String attributeValue = attribute.getValue();
                element.addAttribute(attributeName, attributeValue);
            }
        }
    }

    private static void writeDocument(Document document, Path path) {
        final FileWriter fw;
        XMLWriter xw = null;
        try {
            fw = new FileWriter(concatFilename(path));
            xw = new XMLWriter(fw, STANDARD);

            xw.write(document);
            xw.flush();
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
        STANDARD.setNewlines(true);
        STANDARD.setIndent(true);
        STANDARD.setPadText(true);
        STANDARD.setTrimText(false);
    }
}
