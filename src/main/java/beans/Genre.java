package beans;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Enum that describes allowable film genres
 */
public enum Genre {
    HORROR("Horror"), MYSTERY("Mystery"), THRILLER("Thriller"), COMEDY("Comedy"),
    DRAMA("Drama"), FAMILY("Family"), FANTASY("Fantasy"), ACTION("Action"),
    ROMANCE("Romance"), BIOGRAPHY("Biography"), SCI_FI("Sci-fi"), HISTORY("History"),
    WAR("War"), CRIME("Crime"), MUSICAL("Musical"), ADVENTURE("Adventure"),
    MUSIC("Music"), ANIMATION("Animation"), FILM_NOIR("Film-Noir"), DOCUMENTARY("Documentary"),
    WESTERN("Western"), SPORT("Sport");
    private static final Map<String,Genre> ENUM_MAP;
    private String name;

    Genre(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    static {
        Map<String,Genre> map = new ConcurrentHashMap<String, Genre>();
        for (Genre instance : Genre.values()) {
            map.put(instance.getName().toLowerCase(),instance);
        }
        ENUM_MAP = Collections.unmodifiableMap(map);
    }

    public static Genre get (String name) {
        return ENUM_MAP.get(name.toLowerCase());
    }

    @Override
    public String toString() {
        return name;
    }
}
