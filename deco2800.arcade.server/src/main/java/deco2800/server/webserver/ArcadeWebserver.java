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

	void respondToReplayRequest( Response response ) throws Exception {
		PrintStream body = response.getPrintStream();
		long time = System.currentTimeMillis();

		response.setValue("Content-Type", "text/html");
		response.setValue("Server", "HelloWorld/1.0 (Simple 4.0)");
		response.setDate("Date", time);
		response.setDate("Last-Modified", time);
		
		String bodyString = "";

		ArrayList<String> replayStrings = new ArrayList<String>();
		replayStrings = ArcadeServer.instance().getReplayStorage().getSessionsForGame( "connect4" );

		replayStrings.add( "100001, false, replayers, 1381645619190, " );
		replayStrings.add( "100001, false, replayers, 1381645632133, " );
		
		ArrayList<Session> replays = new ArrayList<Session>();
		//Convert back to sessions
        if (replayStrings != null)
        {
            for (String s : replayStrings)
            {
                String[] tokens = s.split(",");

                int sessionId = Integer.parseInt(tokens[0].replaceAll("\\s+",""));
                boolean recording = Boolean.parseBoolean(tokens[1]);
                String user = tokens[2];
                long dateTime = Long.parseLong(tokens[3].replaceAll("\\s+",""));
                String comments = tokens[4];
                
                Session session = new Session();
                session.sessionId = sessionId;
                session.time = dateTime;
                session.username = user;
                session.recording = recording;
                session.comments = comments;
                
                replays.add( session );
            }
        }
		
		System.out.println( replays );
		
		bodyString = readFile( "webserver/html/template.html", Charset.forName("UTF-8" ) );
		
		String contentString = readFile( "webserver/html/replays.html", Charset.forName("UTF-8" ) );
		
		String tableString = "";
		for ( Session replay : replays ) {
			Date replayDate = new Date( replay.time );
			String replayDateString = DateFormat.getDateTimeInstance().format( replayDate );
			
			Integer itemCount = 0;
			if ( !replay.sessionId.equals( 100001 ) ) {
				 itemCount = ( ArcadeServer.instance().getReplayStorage().getReplay( replay.sessionId ) ).size();
			}
			tableString += String.format( "<tr><td>%s</td><td>%d</td><td>%s</td></tr>", replay.username, itemCount, replayDateString );
		}
		
		contentString = contentString.replace( "#{{tablebody}}", tableString );
		
		bodyString = bodyString.replace( "#{{content}}", contentString );
	
		body.println( bodyString );
		body.close();
	}
	
	public void handle(Request request, Response response) {
		try {
			
			if ( request.getPath().toString().equals( "/") ) {
				respondToReplayRequest( response );
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