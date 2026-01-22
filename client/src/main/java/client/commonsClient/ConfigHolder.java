package client.commonsClient;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class ConfigHolder {

    private final ObjectMapper mapper = new ObjectMapper();
    private final File configFile = new File("config.json");

    private ClientConfig clientConfig;

    /**
     * Modify configuration
     * @param modifier function wherein you can modify the configuration
     */
    public void modify(ConfigModifier modifier) {
        load();
        modifier.modify(this.clientConfig);
        save();
    }

    /**
     * Save configuration to file
     */
    public void save() {
        try {
            mapper.writeValue(configFile, clientConfig);
        } catch (IOException _) {
            System.err.println("Could not save " + configFile.getName());
        }
    }

    /**
     * Get configuration object
     * @return ClientConfig object
     */
    public ClientConfig get() {
        if (this.clientConfig == null) {
            this.load();
        }
        return this.clientConfig;
    }

    /**
     * Load config from file
     */
    public void load() {
        try {
            File configFile = new File("config.json");
            if (!configFile.exists()) {
                ClientConfig newConfig = new ClientConfig();

                Files.writeString(configFile.toPath(),
                        mapper.writeValueAsString(newConfig));

                this.clientConfig = newConfig;
            } else {
                this.clientConfig =
                        mapper.readValue(configFile, ClientConfig.class);
            }
        } catch (Exception _) {
            System.err.println("Could not load " + configFile.getName());
        }
    }

    public interface ConfigModifier {
        /**
         * Function wherein you can modify the configuration
         * @param config the editable client config
         */
        void modify(ClientConfig config);
    }

}
