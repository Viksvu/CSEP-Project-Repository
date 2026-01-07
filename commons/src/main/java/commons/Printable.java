package client;

import java.util.List;

public interface Printable {
    /**
     * Returns all the objects in a list as follows:
     *   If I want:
         *   CSEP-Team-X (Heading)
         *   Members: (Normal Text)
         *   - A
         *   - B
         *   - C
         *   Contact any member for more info (Normal Text)
     *   It should be returned as a list with:
     *   [ReadmeOptions.H1, "CSEP-Team-X", ReadmeOptions.TEXT, "Members: "
     *      , ReadmeOptions.BULLET, "A", "B", "C", ReadmeOptions.BULLETEND,
     *      ReadmeOptions.TEXT, "Contact any member for more info"]
     */
    List<Object> indexing();
}
