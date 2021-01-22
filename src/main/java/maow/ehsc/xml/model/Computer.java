package maow.ehsc.xml.model;

public final class Computer {
    private final String id;
    private final String name;
    private final String ip;
    private final String security;
    private final String icon;
    private final String type;

    public Computer(String id, String name, String ip, String security, String icon, String type) {
        this.id = id;
        this.name = name;
        this.ip = ip;
        this.security = security;
        this.icon = icon;
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getIp() {
        return ip;
    }

    public String getSecurity() {
        return security;
    }

    public String getIcon() {
        return icon;
    }

    public String getType() {
        return type;
    }
}
