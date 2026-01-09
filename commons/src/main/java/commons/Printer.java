package commons;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class Printer {
    /**
     * Downloads the object into a readme file
     * @param thing to print
     */
    public static void print(Printable thing) throws IOException {
        StringBuilder text = new StringBuilder();
        // This is the text that will finally be stored in a file
        List<Object> contents = thing.indexing();
        int number = 0;
        boolean bulletPoints = false;
        boolean numbers = false;
        for (Object o : contents) {
            switch (o) {
                case ReadmeOptions.H1 -> text.append("\n# ");
                case ReadmeOptions.H2 -> text.append("\n## ");
                case ReadmeOptions.H3 -> text.append("\n### ");
                case ReadmeOptions.BULLET -> bulletPoints = true;
                case ReadmeOptions.END_BULLET -> bulletPoints = false;
                case ReadmeOptions.TEXT -> text.append("\n");
                case ReadmeOptions.NUMBERING -> numbers = true;
                case ReadmeOptions.END_NUMBERING -> {
                    numbers = false;
                    number=0;
                }
                default -> {
                    if (o instanceof String) {
                        if (bulletPoints) {
                            text.append("\n- ").append(o);
                        } else if (numbers) {
                            text.append("\n").append(++number).append(". ");
                            text.append((String) o);
                        }
                        else {
                            text.append((String) o);
                        }
                    } else {
                        throw new IllegalArgumentException();
                    }
                }
            }
        }
        String fileText = text.toString();
        FileWriter fw = new FileWriter("test.md");
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write(text.toString().trim());
        bw.flush();
    }

}
