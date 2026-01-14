package client.commonsClient;

import javafx.scene.image.Image;

import java.util.Locale;

public class LanguageObject {
    private final Locale locale;
    private final Image flag;

    /**
     * Public constructor for LanguageObject
     * @param locale the Local for the language
     * @param flag flag icon for the chosen language
     */
    public LanguageObject(Locale locale, Image flag) {
        this.locale = locale;
        this.flag = flag;
    }
    public Locale getLocale() { return locale; }
    public Image getFlag() { return flag; }
}
