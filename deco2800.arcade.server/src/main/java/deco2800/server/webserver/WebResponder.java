package deco2800.server.webserver;

import org.simpleframework.http.Response;

public interface WebResponder {
    
    /**
     * Returns the appropriate view with data.
     * @param response
     * @throws Exception
     */
    public void respond( Response response ) throws Exception;

}
