package am.jsl.listings.domain.attribute;

import java.util.*;

/**
 * An enum containing possible attribute types.
 *
 * @author hamlet
 */
public enum AttributeType {
    TEXT,
    LIST,
    INT,
    DOUBLE,
    BOOLEAN,
    DATE;

    private static final List<String> names = new ArrayList<>();

    static {
        Arrays.stream(values()).map(Enum::name).forEach(names::add);
    }

    public static List<String> names() {
        return names;
    }
}
