package commons.util;

import java.util.List;
import java.util.Objects;

public class EqualsUtil {
    /**
     * Check if list a and b are equals
     * This is persistance safe
     * Cause persistence replaces lists with PersitentBag entities
     * @param a list a to compare
     * @param b list b to compare
     * @return true if the elements in the lis are equal
     * @param <A> Type of objects to compare
     */
    public static <A> boolean equalsPersistentSafe(List<A> a, List<A> b) {
        if (a == b) return true;
        if (a == null || b == null) return false;
        if (a.size() != b.size()) return false;

        for (int i = 0; i < a.size(); i++) {
            if (!Objects.equals(a.get(i), b.get(i))) {
                return false;
            }
        }
        return true;
    }
}
