package client.commonsClient;

/**
 * Class used to load and save client config settings
 */
public class ClientConfig {
    private String serverIp;
    private String locale;

    /**
     * Empty constructor used by jackson object mapper
     */
    public ClientConfig() {}

    /**
     * Public constructor for Client config.
     * @param serverIp server ip address.
     * @param locale local for the ui language.
     */

    public ClientConfig(String serverIp, String locale) {
        this.serverIp = serverIp;
        this.locale = locale;
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
}
