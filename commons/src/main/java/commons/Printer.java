package commons;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class Printer {
    /**
     * Downloads the object into a readme file
     * @param thing to print
     * @param pathname to save to
     */
    @Deprecated
    public static void print(Printable thing, String pathname) throws IOException {
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
                case ReadmeOptions.TEXT -> text.append("  \n");
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
        FileWriter fw = new FileWriter(pathname);
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write(fileText.trim());
        bw.flush();
    }

    /**
     * Downloads the object into a readme file with the file as an input
     * @param thing to download
     * @param file to save to
     * @throws IOException if an output (save) exception is thrown
     */
    public static void print(Printable thing, File file) throws IOException {
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
        FileWriter fw = new FileWriter(file);
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write(fileText.trim());
        bw.flush();
    }

}

