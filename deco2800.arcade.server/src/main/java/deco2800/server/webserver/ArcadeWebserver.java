/*
 * KeyManager
 */
package deco2800.server.webserver;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.SocketAddress;

import java.nio.ByteBuffer;
import java.util.Comparator;
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

import deco2800.arcade.model.Game;

public class ArcadeWebserver implements Container {

	private static final int SERVER_PORT = 8080;
	private Map<String, WebResponder> responders;
	
	/**
	 * Set HTTP headers easily.
	 * 
	 * @param response
	 * @param contentType The HTTP content type
	 */
	public static void setResponseValues(Response response, String contentType)
	{
        long time = System.currentTimeMillis();
        
        response.setValue("Content-Type", contentType);
        response.setValue("Server", "HelloWorld/1.0 (Simple 4.0)");
        response.setDate("Date", time);
        response.setDate("Last-Modified", time);
	}
	
    /**
     * Private struct for describing a Route.
     */
    private class Route
    {
        public String route;
        public String param;
        
        public static final int ROUTE_NAME_INDEX = 1;
        public static final int ROUTE_PARAM_INDEX = 2;
    }
	
    /**
     * Splits a path for a request into parameter and route.
     * @param p Path String
     * @return Route struct
     */
	private Route processPath(String p)
	{
	    Route r = new Route();
	    r.route = "";
	    r.param = "";
        String[] s = p.split("/");
        
        if (s.length > 1) {
            r.route = s[Route.ROUTE_NAME_INDEX];
        }
        
        if (s.length > 2) {
            r.param = s[Route.ROUTE_PARAM_INDEX];
        }
        
        return r;
	}
	
	/**
	 * Handle an incoming webrequest.
	 */
	public void handle(Request request, Response response) {
		try {
			
		    Route r = processPath(request.getPath().toString());
		    
		    //If we have a request to serve a page, use the appropriate responder
			if ( responders.containsKey( r.route ) ) {
			    
				responders.get( r.route ).respond( response, r.param );
				
			} else if ( request.getPath().toString().contains( "js" ) ) {
			    
				PrintStream body = response.getPrintStream();
				setResponseValues(response, "text/javascript");

				body.println( FileReader.readFileUtf8( "webserver/javascript" + request.getPath() ) );
				body.close();
				
			} else if ( request.getPath().toString().contains( "css" ) ){
				
				PrintStream body = response.getPrintStream();
				setResponseValues(response, "text/css");

				body.println( FileReader.readFileUtf8("webserver/style" + request.getPath()) );
				body.close();
				
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
                
            } else {
			    
                badRequest(response);
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	} 
	
	public static void badRequest(Response response) throws IOException
	{
        response.setStatus( Status.BAD_REQUEST );

        PrintStream body = response.getPrintStream();
        body.println( "404 Not Found" );
        body.close();
	}

	/**
	 * Simple comparator to sort the games in alphabetical order, as GameStorageDatabase 
	 * getServerGames() returns an unordered map.
	 */
	
	/**
	 * Simple comparator to sort the games in alphabetical order
	 * @return a comparator to alphabetically sort games by name
	 */
	public static Comparator<Game> alphabeticalGameComparator() {
		return new Comparator<Game>() {
	        @Override  
	        public int compare(Game o1, Game o2) {  
	            int rval = Integer.valueOf( ( o1.name ).compareTo( o2.name) );  
	            if ( rval != 0 ) {
	            	return rval;  
	            }
	            return o1.compareTo( o2 );  
	        }  
	    };
	}
	
	/**
	 * Given a response and a file path to an image, set the HTTP content type appropriately.
	 * @param response
	 * @param path
	 */
	private void setAppropriateImageMimeType(Response response, String path)
	{
        int lastDot = path.lastIndexOf( '.' );
        String ext = path.substring(lastDot + 1);
        
        if ( ext.matches( "(?i).*\\.(jpg|jpeg)" ) ) {
        	setResponseValues(response, "image/jpeg" );
        }
        if ( ext.matches( "(?i).*\\.(png)" ) ) {
        	setResponseValues(response, "image/png" );
        }
        if ( ext.matches( "(?i).*\\.(gif)" ) ) {
        	setResponseValues(response, "image/gif" );
        }
	}
	
	/**
	 * Register all the WebResponders with their appropriate routes.
	 */
	public void initialiseHandlers()
	{
	    responders = new HashMap<String, WebResponder>();
	    responders.put("", new HomeResponder());
	    responders.put("achievements", new AchievementResponder());
	    responders.put("replays", new ReplayResponder());
	    responders.put("games", new GameResponder());
	    responders.put("logo", new LogoResponder());
	    responders.put("achievement_icon", new AchievementIconResponder());
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