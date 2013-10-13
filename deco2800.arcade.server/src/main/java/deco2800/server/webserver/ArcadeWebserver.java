/*
 * KeyManager
 */
package deco2800.server.webserver;

import java.util.ArrayList;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.SocketAddress;

import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;

import org.simpleframework.http.Request;
import org.simpleframework.http.Response;
import org.simpleframework.http.core.Container;
import org.simpleframework.http.core.ContainerServer;
import org.simpleframework.transport.Server;
import org.simpleframework.transport.connect.Connection;
import org.simpleframework.transport.connect.SocketConnection;

public class ArcadeWebserver implements Container {
	
	static String readFile(String path, Charset encoding) 
			throws IOException 
			{
	    RandomAccessFile raf = new RandomAccessFile(path, "r");
	    FileChannel inChannel = raf.getChannel();
	    
		ByteBuffer encoded = ByteBuffer.allocate((int) inChannel.size());
		inChannel.read(encoded);
		
		byte[] bytes = encoded.array();
		
		inChannel.close();
		
		return new String(bytes, encoding);
			}


	public void handle(Request request, Response response) {
		try {
			
			if ( request.getPath().toString().equals( "/") ) {
				PrintStream body = response.getPrintStream();
				long time = System.currentTimeMillis();
	
				response.setValue("Content-Type", "text/html");
				response.setValue("Server", "HelloWorld/1.0 (Simple 4.0)");
				response.setDate("Date", time);
				response.setDate("Last-Modified", time);
				
				String bodyString = "";

				ArrayList<String> replays = new ArrayList<String>();
				//test = ArcadeServer.instance().getReplayStorage().getSessionsForGame( "connect4" );
				replays.add( "1" );
				replays.add( "false" );
				replays.add( "replayers" );
				replays.add( "1381639231100" );
				replays.add( "" );
				replays.add( "2" );
				replays.add( "false" );
				replays.add( "replayers" );
				replays.add( "1381639268788" );
				replays.add( "" );
						
				System.out.println( replays );
				
				bodyString = readFile( "webserver/html/template.html", Charset.forName("UTF-8" ) );
				
				String contentString = readFile( "webserver/html/replays.html", Charset.forName("UTF-8" ) );
				
				String tableString = "";
				for ( int i = 0; i < replays.size(); i += 5 ) {
					tableString += String.format( "<tr><td>%s</td><td>%s</td></tr>", replays.get( i + 2 ), replays.get( i + 3 ) );
				}
				
				contentString = contentString.replace( "#{{tablebody}}", tableString );
				
				bodyString = bodyString.replace( "#{{content}}", contentString );
			
				body.println( bodyString );
				body.close();
			} else if ( request.getPath().toString().contains( "js" ) ){
				
				PrintStream body = response.getPrintStream();
				long time = System.currentTimeMillis();
	
				response.setValue("Content-Type", "text/javascript");
				response.setValue("Server", "HelloWorld/1.0 (Simple 4.0)");
				response.setDate("Date", time);
				response.setDate("Last-Modified", time);

				body.println( readFile( "webserver/javascript" + request.getPath(), Charset.forName("UTF-8" ) ) );
				body.close();
				System.out.println( "Served js" );
			} else if ( request.getPath().toString().contains( "css" ) ){
				
				PrintStream body = response.getPrintStream();
				long time = System.currentTimeMillis();
	
				response.setValue("Content-Type", "text/css");
				response.setValue("Server", "HelloWorld/1.0 (Simple 4.0)");
				response.setDate("Date", time);
				response.setDate("Last-Modified", time);

				body.println( readFile( "webserver/style" + request.getPath(), Charset.forName("UTF-8" ) ) );
				body.close();
				System.out.println( "Served css" );
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
	
	/*
	public static void main(String[] list) throws Exception {
		Container container = new ReplaySystemWebserver();
		Server server = new ContainerServer(container);
		Connection connection = new SocketConnection(server);
		SocketAddress address = new InetSocketAddress(8080);

		connection.connect(address);
	}
	*/
}