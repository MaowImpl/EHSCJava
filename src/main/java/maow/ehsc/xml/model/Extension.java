package maow.ehsc.xml.model;

import java.util.List;

public final class Extension {
    private final String name;
    private final String lang;
    private final String allowSaves;
    private final String intro;
    private final String startingNodes;
    private final String startingMission;
    private final String startingActions;
    private final String startingMusic;
    private final String startingTheme;
    private final String description;
    private final String workshopDescription;
    private final List<String> factions;
    private final Sequencer sequencer;
    private final WorkshopInfo workshopInfo;

    public Extension(String name, String lang, String allowSaves, String intro, String startingNodes, String startingMission, String startingActions, String startingMusic, String startingTheme, String description, String workshopDescription, List<String> factions, Sequencer sequencer, WorkshopInfo workshopInfo) {
        this.name = name;
        this.lang = lang;
        this.allowSaves = allowSaves;
        this.intro = intro;
        this.startingNodes = startingNodes;
        this.startingMission = startingMission;
        this.startingActions = startingActions;
        this.startingMusic = startingMusic;
        this.startingTheme = startingTheme;
        this.description = description;
        this.workshopDescription = workshopDescription;
        this.factions = factions;
        this.sequencer = sequencer;
        this.workshopInfo = workshopInfo;
    }

    public String getName() {
        return name;
    }

    public String getLang() {
        return lang;
    }

    public String getAllowSaves() {
        return allowSaves;
    }

    public String getIntro() {
        return intro;
    }

    public String getStartingNodes() {
        return startingNodes;
    }

    public String getStartingMission() {
        return startingMission;
    }

    public String getStartingActions() {
        return startingActions;
    }

    public String getStartingMusic() {
        return startingMusic;
    }

    public String getStartingTheme() {
        return startingTheme;
    }

    public String getDescription() {
        return description;
    }

    public String getWorkshopDescription() {
        return workshopDescription;
    }

    public List<String> getFactions() {
        return factions;
    }

    public Sequencer getSequencer() {
        return sequencer;
    }

    public WorkshopInfo getWorkshopInfo() {
        return workshopInfo;
    }

    public static class Sequencer {
        private final String target;
        private final String spinupTime;
        private final String flag;
        private final String actions;

        public Sequencer(String target, String spinupTime, String flag, String actions) {
            this.target = target;
            this.spinupTime = spinupTime;
            this.flag = flag;
            this.actions = actions;
        }

        public String getTarget() {
            return target;
        }

        public String getSpinupTime() {
            return spinupTime;
        }

        public String getFlag() {
            return flag;
        }

        public String getActions() {
            return actions;
        }
    }

    public static class WorkshopInfo {
        private final String visibility;
        private final String tags;
        private final String logo;
        private final String id;

        public WorkshopInfo(String visibility, String tags, String logo, String id) {
            this.visibility = visibility;
            this.tags = tags;
            this.logo = logo;
            this.id = id;
        }

        public String getVisibility() {
            return visibility;
        }

        public String getTags() {
            return tags;
        }

        public String getLogo() {
            return logo;
        }

        public String getId() {
            return id;
        }
    }
}