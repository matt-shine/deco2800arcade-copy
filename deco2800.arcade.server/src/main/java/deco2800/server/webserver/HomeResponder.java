package deco2800.server.webserver;

import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.Charset;

import org.simpleframework.http.Response;

public class HomeResponder implements WebResponder {
	public void respond( Response response, String param ) throws IOException  {
		PrintStream body = response.getPrintStream();
		long time = System.currentTimeMillis();

		response.setValue("Content-Type", "text/html");
		response.setValue("Server", "HelloWorld/1.0 (Simple 4.0)");
		response.setDate("Date", time);
		response.setDate("Last-Modified", time);

		String bodyString = FileReader.readFile( "webserver/html/template.html", Charset.forName("UTF-8" ) );
		String contentString = FileReader.readFile( "webserver/html/_home.html", Charset.forName("UTF-8" ) );
		
		bodyString = bodyString.replace( "#{{content}}", contentString );
		
		body.println( bodyString );
		body.close();
	}
}
