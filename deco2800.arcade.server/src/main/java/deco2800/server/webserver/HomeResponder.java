package deco2800.server.webserver;

import java.io.IOException;
import java.io.PrintStream;

import org.simpleframework.http.Response;

public class HomeResponder implements WebResponder {
	
	public HomeResponder() {
		super();
	}
	
	public void respond( Response response, String param ) throws IOException  {
		PrintStream body = response.getPrintStream();

        ArcadeWebserver.setResponseValues(response, "text/html");

        /*
         * Read in the template and populate it with static html from the _home partial
         */
		String bodyString = FileReader.readFileUtf8( "webserver/html/template.html" );
		String contentString = FileReader.readFileUtf8( "webserver/html/_home.html" );
		bodyString = bodyString.replace( "#{{content}}", contentString );
		
		body.println( bodyString );
		body.close();
	}
}
