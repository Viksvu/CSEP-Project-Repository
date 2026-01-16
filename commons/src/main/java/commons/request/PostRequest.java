package commons.request;

public interface PostRequest {
    /**
     * Returns the path the server has to use for this request
     * @return path server has to use for this request
     */
    String serverPath();
}
