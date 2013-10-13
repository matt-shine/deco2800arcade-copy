/*
 * KeyManager
 */
package deco2800.server.webserver;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.SocketAddress;

import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;

import org.simpleframework.http.Request;
import org.simpleframework.http.Response;
import org.simpleframework.http.Status;
import org.simpleframework.http.core.Container;
import org.simpleframework.http.core.ContainerServer;
import org.simpleframework.transport.Server;
import org.simpleframework.transport.connect.Connection;
import org.simpleframework.transport.connect.SocketConnection;

import deco2800.arcade.protocol.replay.types.Session;
import deco2800.server.ArcadeServer;

public class ArcadeWebserver implements Container {
	
	

	
	
	public void handle(Request request, Response response) {
		try {
			
			if ( request.getPath().toString().equals( "/") ) {
				HomeResponder.respondToHomeRequest( response );
			} else if ( request.getPath().toString().equals( "/replays") ) {
				ReplayResponder.respondToReplayRequest( response );
			} else if ( request.getPath().toString().contains( "js" ) ) {
				
				PrintStream body = response.getPrintStream();
				long time = System.currentTimeMillis();
	
				response.setValue("Content-Type", "text/javascript");
				response.setValue("Server", "HelloWorld/1.0 (Simple 4.0)");
				response.setDate("Date", time);
				response.setDate("Last-Modified", time);

				body.println( FileReader.readFile( "webserver/javascript" + request.getPath(), Charset.forName("UTF-8" ) ) );
				body.close();
				System.out.println( "Served js" );
			} else if ( request.getPath().toString().contains( "css" ) ){
				
				PrintStream body = response.getPrintStream();
				long time = System.currentTimeMillis();
	
				response.setValue("Content-Type", "text/css");
				response.setValue("Server", "HelloWorld/1.0 (Simple 4.0)");
				response.setDate("Date", time);
				response.setDate("Last-Modified", time);

				body.println( FileReader.readFile( "webserver/style" + request.getPath(), Charset.forName("UTF-8" ) ) );
				body.close();
				System.out.println( "Served css" );
			} else {
				response.setStatus( Status.BAD_REQUEST );

				PrintStream body = response.getPrintStream();
				body.println( "404 Not Found" );
				body.close();
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	} 

	public static void startServer( ) {
		try {
			Container container = new ArcadeWebserver();
			Server server = new ContainerServer(container);
			Connection connection = new SocketConnection(server);
			SocketAddress address = new InetSocketAddress(8080);
	
			connection.connect(address);
		} catch( Exception e ) {
			
		}
	}
}