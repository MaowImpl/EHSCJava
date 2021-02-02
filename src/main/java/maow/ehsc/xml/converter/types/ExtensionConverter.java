package maow.ehsc.xml.converter.types;

import maow.ehsc.util.Language;
import maow.ehsc.xml.converter.AbstractDocumentConverter;
import maow.ehsc.xml.modifier.AttributeModifier;
import org.dom4j.Document;
import org.dom4j.Element;

public final class ExtensionConverter extends AbstractDocumentConverter {
    public ExtensionConverter(Document document) {
        super(document);
    }

    private String getLanguageCode(AttributeModifier modifier) {
        final String name = modifier.value().toUpperCase();
        final Language language = Language.valueOf(name);
        return language.getCode();
    }

    @Override
    protected Element _convert() {
        return doc()
                .root("HacknetExtension")
                .convert(attribute("lang"), modifier -> {
                    modifier.add(element("Language", modifier.value()));
                    modifier.add(element("WorkshopLanguage", getLanguageCode(modifier)));
                })
                .translate(attribute("name"), element("Name"))
                .translate(attribute("allowSaves"), element("AllowSaves"))
                .translate(element("StartingNodes"), element("VisibleStartingNodes"))
                .skip(
                        element("StartingMission"),
                        element("StartingActions"),
                        element("Description"),
                        element("WorkshopDescription")
                )
                .convert(element("Description"), modifier -> modifier
                        .has(element("WorkshopDescription")).not()
                        .add(element("WorkshopDescription", modifier.text()))
                )
                .skip(
                        element("Faction"),
                        element("StartingTheme")
                )
                .translate(element("StartingMusic"), element("IntroStartupSong"))
                .convert(element("Sequencer"), modifier -> {
                    modifier.convert(attribute("target"), element("SequencerTargetID"));
                    modifier.convert(attribute("spinupTime"), element("SequencerSpinUpTime"));
                    modifier.convert(attribute("flag"), element("SequencerFlagRequiredForStart"));
                    modifier.convert(attribute("actions"), element("ActionsToRunOnSequencerStart"));
                })
                .convert(element("WorkshopInfo"), modifier -> {
                    modifier.convert(attribute("visibility"), element("WorkshopVisibility"));
                    modifier.convert(attribute("tags"), element("WorkshopTags"));
                    modifier.convert(attribute("logo"), element("WorkshopPreviewImagePath"));
                    modifier.convert(attribute("id"), element("WorkshopPublishID"));
                    modifier
                            .has(attribute("id")).not()
                            .add(element("WorkshopPublishID", "NONE"));
                })
                .build();
    }
}