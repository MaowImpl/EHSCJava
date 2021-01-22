package maow.ehsc.util;

public enum Language {
    ENGLISH("English", "en-us"),
    GERMAN("German", "de-de")
    ;

    private final String name;
    private final String id;

    Language(String name, String id) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public static Language fromName(String name) {
        for (Language language : values()) {
            if (language.getName().equals(name)) {
                return language;
            }
        }
        return ENGLISH;
    }
}
