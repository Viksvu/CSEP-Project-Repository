package client.utils;

/**
 * A query param for a request
 * @param key the param key
 * @param value the param value
 */
public record QueryParam(String key, Object... value) {
}
