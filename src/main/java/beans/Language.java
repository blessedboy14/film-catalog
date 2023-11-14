package beans;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public enum Language {
    ENGLISH("en"), RUSSIAN("ru");
    private String name;

    private static final Map<String,Language> ENUM_MAP;

    static {
        Map<String,Language> map = new ConcurrentHashMap<String, Language>();
        for (Language instance : Language.values()) {
            map.put(instance.getName().toLowerCase(),instance);
        }
        ENUM_MAP = Collections.unmodifiableMap(map);
    }

    public static Language get (String name) {
        return ENUM_MAP.get(name.toLowerCase());
    }

    Language(String locale) {
        name = locale;
    }
    public String getName() {
        return name;
    }
    @Override
    public String toString() {
        return name;
    }
}
