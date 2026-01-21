package client.commonsClient;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

public class ConfigWriter {
    private static final ObjectMapper mapper = new ObjectMapper();
    private static final File filename = new File("config.json");

    /**
     * Empty constructor for object mapper.
     */
    public ConfigWriter() {}

    /**
     * Writes new values to ip and locale to config.json
     * @param clientConfig
     */
    public static void write(ClientConfig clientConfig) {
        try {
            mapper.writeValue(filename, clientConfig);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
