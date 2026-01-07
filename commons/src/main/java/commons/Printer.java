package commons;

import java.util.List;

public class Printer {
    /**
     * Downloads the object into a readme file
     * @param thing to print
     */
    public static void print(Printable thing) {
        StringBuilder text = new StringBuilder();
        // This is the text that will finally be stored in a file
        List<Object> contents = thing.indexing();
        boolean bulletPoints = false;
        for (Object o : contents) {
            switch (o) {
                case ReadmeOptions.H1 -> text.append("\n# ");
                case ReadmeOptions.H2 -> text.append("\n## ");
                case ReadmeOptions.H3 -> text.append("\n### ");
                case ReadmeOptions.BULLET -> bulletPoints = true;
                case ReadmeOptions.END_BULLET -> bulletPoints = false;
                case ReadmeOptions.TEXT -> text.append("\n");
                default -> {
                    if (o instanceof String) {
                        if (bulletPoints) {
                            text.append("- ").append(o);
                        } else {
                            text.append((String) o);
                        }
                    } else {
                        throw new IllegalArgumentException();
                    }
                }
            }
        }
        String fileText = text.toString();
    }

}
