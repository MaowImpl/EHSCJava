package maow.ehsc.xml.convert.types;

import maow.ehsc.EHSC;
import maow.ehsc.util.Language;
import maow.ehsc.xml.ElementBuilder;
import maow.ehsc.xml.convert.AbstractDocumentConverter;
import org.dom4j.Document;
import org.dom4j.Element;

public class ExtensionConverter extends AbstractDocumentConverter {
    public ExtensionConverter(Document document) {
        super(document);
    }

    @Override
    protected Element doConversion() {
        return root()
                .setName("HacknetExtension")
                .convertAttribute("lang", (attributeHandler, element) -> {
                    Utils.addElement(element, "Language", attributeHandler);
                    final String value = attributeHandler.getValue();
                    final Language language = Language.valueOf(value.toUpperCase());
                    Utils.addElement(element, "WorkshopLanguage", language.getCode());
                })
                .convertAttribute("name", (attributeHandler, element) -> Utils.addElement(element, "Name", attributeHandler))
                .convertAttribute("allowSaves", (attributeHandler, element) -> Utils.addElement(element, "AllowSaves", attributeHandler))
                .convertElement("StartingNodes", (elementHandler, element) -> Utils.addElement(element, "VisibleStartingNodes", elementHandler))
                .passElement("StartingMission")
                .passElement("StartingActions")
                .convertElement("Description", (elementHandler, element) -> {
                    Utils.addElement(element, "Description", elementHandler);
                    if (!Utils.hasElement(elementHandler, "WorkshopDescription")) {
                        Utils.addElement(element, "WorkshopDescription", elementHandler);
                        EHSC.log("Added new element \"WorkshopDescription\" to element.");
                    }
                })
                .passElement("WorkshopDescription")
                .passElements("Faction")
                .addElement(new ElementBuilder("StartsWithTutorial")
                        .text("false")
                        .build()
                )
                .passElement("StartingTheme")
                .convertElement("StartingMusic", (elementHandler, element) -> Utils.addElement(element, "IntroStartupSong", elementHandler))
                .convertElement("Sequencer", (elementHandler, element) -> elementHandler
                        .convertAttribute("target", (attributeHandler, element1) -> Utils.addElement(element, "SequencerTargetID", attributeHandler))
                        .convertAttribute("spinupTime", (attributeHandler, element1) -> Utils.addElement(element, "SequencerSpinUpTime", attributeHandler))
                        .convertAttribute("flag", (attributeHandler, element1) -> Utils.addElement(element, "SequencerFlagRequiredForStart", attributeHandler))
                        .convertAttribute("actions", (attributeHandler, element1) -> Utils.addElement(element, "ActionsToRunOnSequencerStart", attributeHandler))
                )
                .convertElement("WorkshopInfo", (elementHandler, element) -> {
                    elementHandler
                            .convertAttribute("visibility", (attributeHandler, element1) -> Utils.addElement(element, "WorkshopVisibility", attributeHandler))
                            .convertAttribute("tags", (attributeHandler, element1) -> Utils.addElement(element, "WorkshopTags", attributeHandler))
                            .convertAttribute("id", (attributeHandler, element1) -> Utils.addElement(element, "WorkshopPreviewImagePath", attributeHandler));
                    if (!Utils.hasAttribute(element, "id")) {
                        Utils.addElement(element, "WorkshopPreviewImagePath", "NONE");
                        EHSC.log("Added new element \"WorkshopPreviewImagePath\" to element.");
                    }
                })
                .build();
    }
}
