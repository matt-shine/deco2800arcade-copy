/*
 * KeyManager
 */
package deco2800.arcade.webserver;

import java.util.HashMap;
import java.util.Map;

import static com.badlogic.gdx.Input.Keys.UNKNOWN;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.SocketAddress;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.simpleframework.http.Request;
import org.simpleframework.http.Response;
import org.simpleframework.http.core.Container;
import org.simpleframework.http.core.ContainerServer;
import org.simpleframework.transport.Server;
import org.simpleframework.transport.connect.Connection;
import org.simpleframework.transport.connect.SocketConnection;

import deco2800.arcade.client.network.NetworkClient;
import deco2800.arcade.client.replay.ReplayHandler;

public class ReplaySystemWebserver implements Container {

	private static NetworkClient networkClient;
	
	static String readFile(String path, Charset encoding) 
			throws IOException 
			{
		byte[] encoded = Files.readAllBytes(Paths.get(path));
		return encoding.decode(ByteBuffer.wrap(encoded)).toString();
			}

	public void handle(Request request, Response response) {
		try {
			System.out.println( request.getPath() );
			
			PrintStream body = response.getPrintStream();
			long time = System.currentTimeMillis();

			response.setValue("Content-Type", "text/html");
			response.setValue("Server", "HelloWorld/1.0 (Simple 4.0)");
			response.setDate("Date", time);
			response.setDate("Last-Modified", time);


			ReplayHandler replayHandler = new ReplayHandler( ReplaySystemWebserver.networkClient );
			replayHandler.requestSessionList( "connect4" );
			
			body.println( readFile( "html/welcome.html", StandardCharsets.UTF_8 ) );
			body.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
	} 

	public static void startServer( NetworkClient client ) {
		try {
			ReplaySystemWebserver.networkClient = client;
			Container container = new ReplaySystemWebserver();
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