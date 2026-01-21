package client.commonsClient;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;

/**
 * Class used to read and write to config file.
 */
public class ConfigLoader {
    private static final ObjectMapper mapper = new ObjectMapper();
    private static ClientConfig clientConfig;

    /**
     * Loads the ip and locale from the file config.json.
     */
    public static void loadConfig() {
        try {
            File configFile = new File("config.json");
            clientConfig = mapper.readValue(configFile, ClientConfig.class);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * getter for clientConfig.
     * @return clientConfig.
     */
    public static ClientConfig getClientConfig() {
        return clientConfig;
    }

    /**
     * saves the new values of clientConfig to the file.
     */
    public static void save() {
        try {
            mapper.writeValue(new File("config.json"), clientConfig);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
