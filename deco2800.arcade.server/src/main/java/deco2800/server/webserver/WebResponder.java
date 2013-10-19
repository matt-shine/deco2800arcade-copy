package deco2800.server.webserver;

import java.io.IOException;

import org.simpleframework.http.Response;

import deco2800.server.database.DatabaseException;

public interface WebResponder {
    
    /**
     * Returns the appropriate view with data.
     * @param response, the Response object to print the response to
     * @param param, the parameter passed after the route in the URL
     * @throws Exception if the response could not be generated
     */
    void respond( Response response, String param ) throws IOException, DatabaseException;

}
