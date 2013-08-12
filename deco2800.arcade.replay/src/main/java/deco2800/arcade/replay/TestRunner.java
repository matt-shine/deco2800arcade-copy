package deco2800.arcade.replay;


public class TestRunner {

	public TestRunner() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] argv) throws Exception {
		ReplayHandler replayHandler = new ReplayHandler();
		replayHandler.addReplayEventListener( new ReplayEventListener() {
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
		} );
		

		replayHandler.startRecording();

		Thread.sleep( 500 );
		
		ReplayNode node = new ReplayNode( "piece_move" );
		node.addItem( "piece_id", new ReplayItem( new Integer( 6 ) ) );
		node.addItem( "new_x", new ReplayItem( new Integer( 8 ) ) );
		node.addItem( "new_y", new ReplayItem( new Integer( 4 ) ) );
		replayHandler.pushEvent( node );

		Thread.sleep( 1500 );
		
		node = new ReplayNode( "piece_move" );
		node.addItem( "piece_id", new ReplayItem( new Integer( 12 ) ) );
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
