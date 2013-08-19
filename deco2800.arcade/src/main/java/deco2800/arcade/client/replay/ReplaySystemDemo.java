package deco2800.arcade.client.replay;

import deco2800.arcade.client.network.NetworkClient;
import deco2800.arcade.client.network.listener.ReplayListener;

public class ReplaySystemDemo {

	public ReplaySystemDemo() {
		// TODO Auto-generated constructor stub
	}
	
	private static ReplayEventListener initReplayEventListener()
	{
	    return new ReplayEventListener() {
            public void replayEventReceived( String eType, Object eData ) {
                if ( eType == "node_pushed" ) {
                    System.out.println( eType );
                    System.out.println( eData );
                }
                if ( eType == "event_pushed" ) {
                    System.out.println( eType );
                    //System.out.println( eData );
                }
                if ( eType == "replay_reset" ) {
                    System.out.println( "replay reset" );
                }
                if ( eType == "playback_finished" ) {
                    System.out.println( "playback finished" );
                }
                
                if ( eType == "piece_move" ) {
                    System.out.println( "Move: " + eData );
                }
            }
        }; 
	}

	public static void main(String[] argv) throws Exception {
	    
	    //Networking, not very hard.
	    
	    //Set up a dummy network client to use for testing
	    NetworkClient client = new NetworkClient("127.0.0.1", 54555, 54777);
		
	    //Our replay handler
	    ReplayHandler replayHandler = new ReplayHandler(client);
	    
		//Set up our listener on this end, we can either use a constructor
		ReplayListener replayListener = new ReplayListener(replayHandler);
		
		//Or we can create a null one and set it later (for compatibility)
		replayListener = new ReplayListener();
		replayListener.setHandler(replayHandler);
		
		//Our client needs to know about this listener
	    client.addListener(replayListener);
	    
	    //Ping the server
	    replayHandler.sendSimpleMessageToServer();
	    
	    
		//Our replay handler has to know about all the different functions
		replayHandler.addReplayEventListener(initReplayEventListener());
		replayHandler.startRecording();
		
		//Declare an event to be registered in the factory, we can pass arrays.
		ReplayNodeFactory.registerEvent("piece_move",
		                                new String[]{"piece_id",
		                                             "new_x",
		                                             "new_y"}
		                               );
		
		//Or just use varargs to pass a list, redefinitions silently overwrite.
	    ReplayNodeFactory.registerEvent("piece_move",
                                              "piece_id",
                                              "new_x",
                                              "new_y"
                                        );

		Thread.sleep( 500 );
		
		//Recreate event from factory definition (once again, pass array or varargs.
		replayHandler.pushEvent(
		        ReplayNodeFactory.createReplayNode(
		                "piece_move",
		                6, 8, 4
		        )
		);

		Thread.sleep( 1500 );
		
		//Can still create nodes the old way too.
		ReplayNode node;
		node = new ReplayNode( "piece_move" );
		node.addItem( "piece_id", new ReplayItem( new Integer( 12 ) ) ); //New integer is unnecessary.
		node.addItem( "new_x", new ReplayItem( new Integer( 3 ) ) );
		node.addItem( "new_y", new ReplayItem( new Integer( 4 ) ) );
		replayHandler.pushEvent( node );
		
		Thread.sleep( 100 );
		
		node = new ReplayNode( "piece_move" );
		node.addItem( "piece_id", new ReplayItem( new Integer( 3 ) ) );
		node.addItem( "new_x", new ReplayItem( new Integer( 8 ) ) );
		node.addItem( "new_y", new ReplayItem( new Integer( 4 ) ) );
		replayHandler.pushEvent( node );
		
		
		
		System.out.println( "Recording complete" );
		Thread.sleep( 2000 );
		System.out.println( "Starting playback" );
		
		replayHandler.startPlayback();

	}
}
