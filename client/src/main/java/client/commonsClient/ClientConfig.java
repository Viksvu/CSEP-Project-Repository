package client.commonsClient;

import java.util.Set;
import java.util.HashSet;

/**
 * Class used to load and save client config settings
 */
public class ClientConfig {

    private Set<String> recipeLanguageFilters;

    private String serverIp = "http://127.0.0.1:8080/";
    private String socketIp = "ws://localhost:8080/ws";
    private String locale = "en";

    /**
     * Empty constructor used by jackson object mapper
     */
    public ClientConfig() {
        this.recipeLanguageFilters = new HashSet<>();
    }

    /**
     * Public constructor for Client config.
     * @param serverIp server ip address.
     * @param locale local for the ui language.
     */

    public ClientConfig(String serverIp, String locale) {
        this.serverIp = serverIp;
        this.locale = locale;
        this.recipeLanguageFilters = new HashSet<>();
    }

    /**
     * getter for ip address.
     * @return ip address.
     */
    public String getServerIp() {
        return serverIp;
    }

    /**
     * setter for ip address.
     * @param serverIp ip address.
     */
    public void setServerIp(String serverIp) {
        this.serverIp = serverIp;
    }

    /**
     * getter for locale.
     * @return locale.
     */
    public String getLocale() {
        return locale;
    }

    /**
     * setter for locale.
     * @param locale locale.
     */
    public void setLocale(String locale) {
        this.locale = locale;
    }

    /**
     * Get the current address for the websocket
     * @return the current address for the websocket
     */
    public String getSocketIp() {
        return socketIp;
    }

    /**
     * Set the current address for the websocket
     * @param socketIp the new address for the websocket
     */
    public void setSocketIp(String socketIp) {
        this.socketIp = socketIp;
    }

    /**
     * Get the current recipe language filters
     * @return Set of recipe language filters
     */
    public Set<String> getRecipeLanguageFilters() {
        return recipeLanguageFilters;
    }

    /**
     * Set the recipe language recipe filters
     * @param recipeLanguageFilters set the new set of language recipe filters
     */
    public void setRecipeLanguageFilters(Set<String> recipeLanguageFilters) {
        this.recipeLanguageFilters = recipeLanguageFilters;
    }
}
