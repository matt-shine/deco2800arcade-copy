/*
 * KeyManager
 */
package deco2800.server.webserver;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.SocketAddress;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

import org.simpleframework.http.Request;
import org.simpleframework.http.Response;
import org.simpleframework.http.Status;
import org.simpleframework.http.core.Container;
import org.simpleframework.http.core.ContainerServer;
import org.simpleframework.transport.Server;
import org.simpleframework.transport.connect.Connection;
import org.simpleframework.transport.connect.SocketConnection;

public class ArcadeWebserver implements Container {

	private static final int SERVER_PORT = 8080;
	private Map<String, WebResponder> responders;
	
	/**
	 * Set HTTP headers easily.
	 * @param response
	 * @param contentType The HTTP content type
	 */
	private void setResponseValues(Response response, String contentType)
	{
        long time = System.currentTimeMillis();
        
        response.setValue("Content-Type", contentType);
        response.setValue("Server", "HelloWorld/1.0 (Simple 4.0)");
        response.setDate("Date", time);
        response.setDate("Last-Modified", time);
	}
	
	/**
	 * Get File contents as a UTF-8 String
	 * @param path Path to file
	 * @return File contents
	 * @throws IOException
	 */
	private String fileContents(String path) throws IOException
	{
	    return FileReader.readFile( path, Charset.forName("UTF-8" ) );
	}
	
	/**
	 * Handle an incoming webrequest.
	 */
	public void handle(Request request, Response response) {
		try {
			
		    //If we have a request to serve a page, use the appropriate responder
			if ( responders.containsKey(request.getPath().toString()) ) {
			    
				responders.get(request.getPath().toString()).respond( response );
				
			} else if ( request.getPath().toString().contains( "js" ) ) {
			    
				PrintStream body = response.getPrintStream();
				setResponseValues(response, "text/javascript");

				body.println( fileContents( "webserver/javascript" + request.getPath() ) );
				body.close();
				
				System.out.println( "Served js: " + "webserver/javascript" + request.getPath() );
			} else if ( request.getPath().toString().contains( "css" ) ){
				
				PrintStream body = response.getPrintStream();
				setResponseValues(response, "text/css");

				body.println( fileContents("webserver/style" + request.getPath()) );
				body.close();
				
				System.out.println( "Served css: " + "webserver/style" + request.getPath() );
			//Serve an image if one is requested
			} else if ( request.getPath().toString().matches( "(?i).*\\.(jpg|jpeg|png|gif)" ) ){
                
			    setAppropriateImageMimeType(response, request.getPath().toString());
			    
			    try
			    {
			        ByteBuffer file = FileReader.readBinaryFile("webserver/image" + request.getPath());

                    OutputStream out = response.getOutputStream(file.capacity());
                    
                    out.write(file.array());
                    out.close();
                
                } catch (Exception e)
                {
                    badRequest(response);
                }
                
                System.out.println( "Served css: " + "webserver/image" + request.getPath() );
            } else {
			    
                badRequest(response);
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	} 
	
	private void badRequest(Response response) throws IOException
	{
        response.setStatus( Status.BAD_REQUEST );

        PrintStream body = response.getPrintStream();
        body.println( "404 Not Found" );
        body.close();
	}
	
	/**
	 * Given a response and a file path to an image, set the HTTP content type appropriately.
	 * @param response
	 * @param path
	 */
	private void setAppropriateImageMimeType(Response response, String path)
	{
        int lastDot = path.lastIndexOf('.');
        String ext = path.substring(lastDot + 1);
        
        if (ext.matches("(?i).*\\.(jpg|jpeg)")) setResponseValues(response, "image/jpeg");
        if (ext.matches("(?i).*\\.(png)")) setResponseValues(response, "image/png");
        if (ext.matches("(?i).*\\.(gif)")) setResponseValues(response, "image/gif");
	}
	
	/**
	 * Register all the WebResponders with their appropriate routes.
	 */
	public void initialiseHandlers()
	{
	    responders = new HashMap<String, WebResponder>();
	    responders.put("/", new HomeResponder());
	    responders.put("/achievements", new AchievementResponder());
	    responders.put("/replays", new ReplayResponder());
	    responders.put("/games", new GameResponder());
	}

	public static void startServer( ) {
		try {
			ArcadeWebserver container = new ArcadeWebserver();
			container.initialiseHandlers();
			Server server = new ContainerServer( container );
			Connection connection = new SocketConnection( server );
			SocketAddress address = new InetSocketAddress( SERVER_PORT );
	
			connection.connect(address);
		} catch( Exception e ) {
			
		}
	}
}