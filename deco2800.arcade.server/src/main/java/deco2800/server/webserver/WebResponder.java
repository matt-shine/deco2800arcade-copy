package deco2800.server.webserver;

import org.simpleframework.http.Response;

public interface WebResponder {
    
    /**
     * Returns the appropriate view with data.
     * @param response, the Response object to print the response to
     * @param param, the parameter passed after the route in the URL
     * @throws Exception if the response could not be generated
     */
    public void respond( Response response, String param ) throws Exception;

}
