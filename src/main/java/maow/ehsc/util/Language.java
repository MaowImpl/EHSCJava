package maow.ehsc.util;

public enum Language {
    ENGLISH("en-us"),
    GERMAN("de-de"),
    FRENCH("fr-be"),
    RUSSIAN("ru-ru"),
    SPANISH("es-ar"),
    KOREAN("ko-kr"),
    JAPANESE("ja-jp"),
    CHINESE("zh-cn"),
    ;

    private final String code;

    Language(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
