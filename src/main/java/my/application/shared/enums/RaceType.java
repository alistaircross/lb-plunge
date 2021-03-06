package my.application.shared.enums;

import java.util.HashMap;
import java.util.Map;

public enum RaceType {
    HORSE, GREYHOUND, HARNESS;

    private static final Map<String, RaceType> myMapping;

    static {
        myMapping = new HashMap<String, RaceType>();
        myMapping.put("horse", HORSE);
        myMapping.put("thoroughbred", HORSE);
        myMapping.put("greyhound", GREYHOUND);
        myMapping.put("harness", HARNESS);
    }

    public static RaceType getRaceTypeForString(String rawRaceType) {
        return myMapping.get(rawRaceType.toLowerCase());
    }


}
