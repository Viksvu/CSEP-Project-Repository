package client.commonsClient;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Injector;

import java.io.File;
import java.nio.file.Files;

/**
 * Class used to read and write to config file.
 */
public class ConfigLoader {
    private static final ObjectMapper mapper = new ObjectMapper();

    /**
     * Loads the ip and locale from the file config.json.
     */
    public static ClientConfig loadConfig() {
        try {
            File configFile = new File("config.json");
            if (!configFile.exists()) {
                Files.writeString(configFile.toPath(), mapper.writeValueAsString(new ClientConfig("http://127.0.0.1:8080/", "nl")));
            }
            return mapper.readValue(configFile, ClientConfig.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
