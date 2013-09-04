package deco2800.arcade.chess;

import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.ImageObserver;
import java.util.*;
import java.awt.image.BufferedImage;
import java.awt.Image;

import deco2800.arcade.client.GameClient;
import deco2800.arcade.client.network.NetworkClient;
import deco2800.arcade.model.Player;
import deco2800.arcade.model.Game.ArcadeGame;
import deco2800.arcade.chess.*;
import deco2800.arcade.chess.pieces.King;
import deco2800.arcade.chess.pieces.Piece;

import java.awt.*;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;

import deco2800.arcade.model.Game;
import deco2800.arcade.model.Game.ArcadeGame;
import deco2800.arcade.model.Player;
import deco2800.arcade.protocol.game.GameStatusUpdate;
import deco2800.arcade.client.GameClient;
import deco2800.arcade.client.network.NetworkClient;


@ArcadeGame(id="chess")
public class Chess extends GameClient implements InputProcessor{

	//This shows whether a piece is selected and ready to move.
	boolean moving = false;
	Piece movingPiece = null;
	
	//Sprite offsets
    int horizOff = SCREENWIDTH/2-256;
    int verticOff = SCREENHEIGHT/2-256;
    int pieceHorizOff = 24;
    int pieceVerticOff = 24;
	
	//Piece positions
	//x-co-ords
	int[] whiteRook1Pos, whiteKnight1Pos, whiteBishop1Pos, whiteKingPos, whiteQueenPos,
		whiteBishop2Pos, whiteKnight2Pos, whiteRook2Pos;
	int[] blackRook1Pos, blackKnight1Pos, blackBishop1Pos, blackKingPos, blackQueenPos,
		blackBishop2Pos, blackKnight2Pos, blackRook2Pos;
	int[] whitePawn0Pos, whitePawn1Pos, whitePawn2Pos, whitePawn3Pos, whitePawn4Pos, whitePawn5Pos, 
		whitePawn6Pos, whitePawn7Pos;
	int[] blackPawn0Pos, blackPawn1Pos, blackPawn2Pos, blackPawn3Pos, blackPawn4Pos, blackPawn5Pos, 
		blackPawn6Pos, blackPawn7Pos;
	
	Board board;
	
	boolean players_move;
	boolean playing;
	Piece piece = new King(false);
	private String[] players = new String[2]; // The names of the players: the local player is always players[0]
	
	private OrthographicCamera camera;
	
	private InputMultiplexer inputMultiplexer = new InputMultiplexer(this);
	
	public static final int SCREENHEIGHT = 720;
	public static final int SCREENWIDTH = 1280;
	
	private ShapeRenderer shapeRenderer;
	private SpriteBatch batch;
	private BitmapFont font;

	private String statusMessage;
	
	private Texture chessBoard;
	
	private Sprite blackBishop1, blackBishop2, blackRook1, 
	blackRook2, blackKnight1, blackKnight2, blackKing, blackQueen, 
	blackPawn0, blackPawn1, blackPawn2, blackPawn3, blackPawn4, blackPawn5, blackPawn6, blackPawn7, 
	whiteBishop2, whiteBishop1, whiteRook1, whiteRook2, whiteKnight1, whiteKnight2, whiteKing, whiteQueen, 
	whitePawn0, whitePawn1, whitePawn2, whitePawn3, whitePawn4, whitePawn5, whitePawn6, whitePawn7;
	
	//Network client for communicating with the server.
	//Should games reuse the client of the arcade somehow? Probably!
	private NetworkClient networkClient;
	
	private Texture texture;
	
	/**
	 * Initialises a new game 
	 */
	public Chess(Player player, NetworkClient networkClient) {
		
		super(player, networkClient);
		initPiecePos();
		board = new Board();
		this.networkClient = networkClient; //this is a bit of a hack
		players[0] = player.getUsername();
		players[1] = "Player 2"; //TODO eventually the server may send back the opponent's actual username
		
	}
	
	public void MouseClicked(MouseEvent e)	{
		
		
	}
	
	@Override
	public void create() {
		super.create();
		//Initialise camera
		camera = new OrthographicCamera();
		camera.setToOrtho(false);
		
		//Necessary for rendering
		shapeRenderer = new ShapeRenderer();
		font = new BitmapFont();
		font.setScale(2);
		batch = new SpriteBatch();
		
		Texture.setEnforcePotImages(false);
		
		inputMultiplexer.addProcessor(this);
		Gdx.input.setInputProcessor(inputMultiplexer);
		
		
		// load the images for the droplet and the bucket, 512x512 pixels each
		chessBoard = new Texture(Gdx.files.classpath("imgs/chessboard.png"));
		
		//Pieces
		blackBishop1 = new Sprite(new Texture(Gdx.files.classpath("imgs/try2/Black B.png")));
		blackRook1 = new Sprite(new Texture(Gdx.files.classpath("imgs/try2/Black R.png")));
		blackKnight1 = new Sprite(new Texture(Gdx.files.classpath("imgs/try2/Black N.png")));
		blackKing = new Sprite(new Texture(Gdx.files.classpath("imgs/try2/Black K.png")));
		blackQueen = new Sprite(new Texture(Gdx.files.classpath("imgs/try2/Black Q.png")));
		blackPawn0 = new Sprite(new Texture(Gdx.files.classpath("imgs/try2/Black P.png")));
		
		whiteBishop1 = new Sprite(new Texture(Gdx.files.classpath("imgs/try2/White B.png")));
		whiteRook1 = new Sprite(new Texture(Gdx.files.classpath("imgs/try2/White R.png")));
		whiteKnight1 = new Sprite(new Texture(Gdx.files.classpath("imgs/try2/White N.png")));
		whiteKing = new Sprite(new Texture(Gdx.files.classpath("imgs/try2/White K.png")));
		whiteQueen = new Sprite(new Texture(Gdx.files.classpath("imgs/try2/White Q.png")));
		whitePawn0 = new Sprite(new Texture(Gdx.files.classpath("imgs/try2/White P.png")));
		
		whiteRook2 = whiteRook1;
		whiteBishop2= whiteBishop1;
		whiteKnight2 = whiteKnight1;
		blackBishop2 = blackBishop1;
		blackKnight2 = blackKnight1;
		blackRook2 = blackRook1;
		
		whitePawn1 = whitePawn0;
		whitePawn2 = whitePawn0;
		whitePawn3 = whitePawn0;
		whitePawn4 = whitePawn0;
		whitePawn5 = whitePawn0;
		whitePawn6 = whitePawn0;
		whitePawn7 = whitePawn0;	
		blackPawn1 = blackPawn0;
		blackPawn2 = blackPawn0;
		blackPawn3 = blackPawn0;
		blackPawn4 = blackPawn0;
		blackPawn5 = blackPawn0;
		blackPawn6 = blackPawn0;
		blackPawn7 = blackPawn0;
		
	}

	@Override
	public void dispose() {
		super.dispose();
	}

	@Override
	public void pause() {
		super.pause();
	}
	
	/**
	 * Render the current state of the game and process updates
	 */
	@Override
	public void render() {
		
		
		//White background
		Gdx.gl.glClearColor(255, 255, 255, 1);
	    Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
	   
	    // tell the camera to update its matrices.
	    camera.update();
	    shapeRenderer.setProjectionMatrix(camera.combined);
	    batch.setProjectionMatrix(camera.combined);
	    
	    batch.begin();
	    
	    // Board
	    batch.draw(chessBoard, horizOff, verticOff);
	    
	    //monkey balls
	    
	    
	    //black pieces
	    batch.draw(blackRook1, blackRook1Pos[0], blackRook1Pos[1]);
	    batch.draw(blackKnight1, blackKnight1Pos[0], blackKnight1Pos[1]);	
	    batch.draw(blackBishop1, blackBishop1Pos[0], blackBishop1Pos[1]);
	    batch.draw(blackQueen, blackQueenPos[0], blackQueenPos[1]);
	    batch.draw(blackKing, blackKingPos[0], blackKingPos[1]);
	    batch.draw(blackBishop2, blackBishop2Pos[0], blackBishop2Pos[1]);	
	    batch.draw(blackKnight2, blackKnight2Pos[0], blackKnight2Pos[1]);
	    batch.draw(blackRook2, blackRook2Pos[0], blackRook2Pos[1]);
	    
	    
	    //whitepieces
	    batch.draw(whiteRook1, whiteRook1Pos[0], whiteRook1Pos[1]);
	    batch.draw(whiteBishop1, whiteBishop1Pos[0], whiteBishop1Pos[1]);	
	    batch.draw(whiteKnight1, whiteKnight1Pos[0], whiteKnight1Pos[1]);
	    batch.draw(whiteQueen, whiteQueenPos[0], whiteQueenPos[1]);
	    batch.draw(whiteKing, whiteKingPos[0], whiteKingPos[1]);
	    batch.draw(whiteBishop2, whiteBishop2Pos[0], whiteBishop2Pos[1]);	
	    batch.draw(whiteKnight2, whiteKnight2Pos[0], whiteKnight2Pos[1]);
	    batch.draw(whiteRook2, whiteRook2Pos[0], whiteRook2Pos[1]);
	    
	    
	    //pawns white
	    batch.draw(whitePawn0, whitePawn0Pos[0], whitePawn0Pos[1]);
	    batch.draw(whitePawn1, whitePawn1Pos[0], whitePawn1Pos[1]);
	    batch.draw(whitePawn2, whitePawn2Pos[0], whitePawn2Pos[1]);
	    batch.draw(whitePawn3, whitePawn3Pos[0], whitePawn3Pos[1]);
	    batch.draw(whitePawn4, whitePawn4Pos[0], whitePawn4Pos[1]);
	    batch.draw(whitePawn5, whitePawn5Pos[0], whitePawn5Pos[1]);
	    batch.draw(whitePawn6, whitePawn6Pos[0], whitePawn6Pos[1]);
	    batch.draw(whitePawn7, whitePawn7Pos[0], whitePawn7Pos[1]);
	    //pawns black
	    batch.draw(blackPawn0, blackPawn0Pos[0], blackPawn0Pos[1]);
	    batch.draw(blackPawn1, blackPawn1Pos[0], blackPawn1Pos[1]);
	    batch.draw(blackPawn2, blackPawn2Pos[0], blackPawn2Pos[1]);
	    batch.draw(blackPawn3, blackPawn3Pos[0], blackPawn3Pos[1]);
	    batch.draw(blackPawn4, blackPawn4Pos[0], blackPawn4Pos[1]);
	    batch.draw(blackPawn5, blackPawn5Pos[0], blackPawn5Pos[1]);
	    batch.draw(blackPawn6, blackPawn6Pos[0], blackPawn6Pos[1]);
	    batch.draw(blackPawn7, blackPawn7Pos[0], blackPawn7Pos[1]);
	    
	    batch.end();

	    super.render();

	   
		
	}
	
	@Override
	public void resize(int arg0, int arg1) {
		super.resize(arg0, arg1);
	}

	@Override
	public void resume() {
		super.resume();
	}
	
	
	
	/**
	 * Ends the game
	 */
	private void finishGame() {
		
	}
	
	/**
	 * Ends the game declaring the forfeiting player the loser
	 * 
	 * @param team
	 * 		The team that is forfeiting the match
	 * 				- False means white
	 * 				- True means black
	 */
	private void forfeit(boolean team) {
		
	}

	@Override
	public deco2800.arcade.model.Game getGame() {
		return null;
		// TODO Auto-generated method stub
	}

	public void paint(Graphics g){
		
		
	}

	@Override
	public boolean keyDown(int arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyTyped(char arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyUp(int arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int arg0, int arg1) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int x, int y, int pointer, int button) {
		System.out.println("Co-ords: " + x + ", " + y);
		/*
		if(!moving) {
			movingPiece = checkSquare(x, y);
			moving = true;
			return true;
		} else {
			int[] newPos = determineSquare(x, y);
			board.movePiece(movingPiece, newPos);
			movePieceGraphic();
			moving = false;
			return true;
		}
		*/
		movePieceGraphic();
		return true;
	}

	@Override
	public boolean touchDragged(int arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchUp(int arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub
		return false;
	}
	
	/**
	 * Takes in an x and y co-ordinate and returns which piece is on that 
	 * square.
	 * 
	 * @param x
	 * 		X co-ordinate of mouse click
	 * @param y
	 * 		Y co-ordinate of mouse click
	 * @return
	 * 		Piece on the square that was clicked on
	 */
	private Piece checkSquare(int x, int y) {
		
		int[] square = determineSquare(x,y);		
		Piece onSquare;
		
		try {
			onSquare = board.getPiece(square);
		} catch (IndexOutOfBoundsException e) {
			return null;
		}
		
		System.out.println("Square clicked was: " + square[0] + " " + square[1]);
		
		return onSquare;
	}
	
	private int[] determineSquare(int x, int y) {
		int xSquare = -1;
		int ySquare = -1;
		
		//Determine x square
		for(int i=0; i<8; i++) {
			int j=i;
			if((y >= (97 + (437/8)*i )) && (y <= (97 + (437/8)*(j+1) ))) {
				xSquare = i;
			}
		}
		
		//Determine y square
		for(int i=0; i<8; i++) {
			if((x >= (412 + (583/8)*i )) && (x <= (412 + (583/8)*(i+1) ))) {
				ySquare = i;
			}
		}
		
		switch(xSquare) {
		case 0:
			xSquare = 7;
			break;
		case 1:
			xSquare = 6;
			break;
		case 2:
			xSquare = 5;
			break;
		case 3:
			xSquare = 4;
			break;
		case 4:
			xSquare = 3;
			break;
		case 5:
			xSquare = 2;
			break;
		case 6:
			xSquare = 1;
			break;
		case 7:
			xSquare = 0;
			break;
		default:
			xSquare = -1;
			break;
	}
		
		int[] returnValue = {xSquare, ySquare};
		return returnValue;
	}
	
	/**
	 * Initialised piece start positions as instance variables.  This allows 
	 * them to be accessed by other methods therefore allowing movements.
	 */
	void initPiecePos() {
		int[] pos = {25 + horizOff, 25  + verticOff};
		whiteRook1Pos = pos;
		int[] pos1 = {85 + horizOff, 25 + verticOff};
		whiteKnight1Pos = pos1;
		int[] pos2 = {145 + horizOff, 25 + verticOff};
		whiteBishop1Pos = pos2;
		int[] pos3 = {260 + horizOff, 25 + verticOff};
		whiteKingPos = pos3;
		int[] pos4 = {205 + horizOff, 25 + verticOff};
		whiteQueenPos = pos4;
		int[] pos5 = {320 + horizOff, 25 + verticOff};
		whiteBishop2Pos = pos5;
		int[] pos6 = {380 + horizOff, 25 + verticOff};
		whiteKnight2Pos = pos6;
		int[] pos7 = {435 + horizOff, 25 + verticOff};
		whiteRook2Pos = pos7;
		
		int[] pos8 = {25 + horizOff, 435 + verticOff};
		blackRook1Pos = pos8;
		int[] pos9 = {145 + horizOff,435 + verticOff};
		blackKnight1Pos = pos9;
		int[] pos10 = {85 + horizOff, 435 + verticOff};
		blackBishop1Pos = pos10;
		int[] pos11 = {260 + horizOff, 435 + verticOff};
		blackKingPos = pos11;
		int[] pos12 = {205 + horizOff, 435 + verticOff};
		blackQueenPos = pos12;
		int[] pos13 = {320 + horizOff, 435 + verticOff};
		blackBishop2Pos = pos13;
		int[] pos14 = {380 + horizOff, 435  + verticOff};
		blackKnight2Pos = pos14;
		int[] pos15 = {435 + horizOff, 435  + verticOff};
		blackRook2Pos = pos15;
		
		int[] pos16 = {25 + horizOff, 90 + verticOff};
		whitePawn0Pos = pos16;
		int[] pos17 = {85 + horizOff, 90 + verticOff};
		whitePawn1Pos = pos17;
		int[] pos18 = {145 + horizOff, 90 + verticOff};
		whitePawn2Pos = pos18;
		int[] pos19 = {205 + horizOff, 90 + verticOff};
		whitePawn3Pos = pos19; 
		int[] pos20 = {260 + horizOff, 90 + verticOff};
		whitePawn4Pos = pos20;
		int[] pos21 = {320 + horizOff, 90 + verticOff};
		whitePawn5Pos = pos21;
		int[] pos22 = {380 + horizOff, 90 + verticOff};
		whitePawn6Pos = pos22;
		int[] pos23 = {435 + horizOff, 90 + verticOff};
		whitePawn7Pos = pos23;
		
		int[] pos24 = {25 + horizOff, 380 + verticOff};
		blackPawn0Pos = pos24;
		int[] pos25 = {85 + horizOff, 380 + verticOff};
		blackPawn1Pos = pos25;
		int[] pos26 = {145 + horizOff, 380 + verticOff};
		blackPawn2Pos = pos26;
		int[] pos27 = {205 + horizOff, 380 + verticOff};
		blackPawn3Pos = pos27;
		int[] pos28 = {260 + horizOff, 380 + verticOff};
		blackPawn4Pos = pos28;
		int[] pos29 = {320 + horizOff, 380 + verticOff};
		blackPawn5Pos = pos29;
		int[] pos30 = {380 + horizOff, 380 + verticOff};
		blackPawn6Pos = pos30;
		int[] pos31 = {435 + horizOff, 380 + verticOff};
		blackPawn7Pos = pos31;
	}
	
	/**
	 * Moves all the pieces to their correct places on the board
	 */
	void movePieceGraphic() {
		
		for (FixedSizeList<Piece> row : board.Board_State) {
			for (Piece piece : row) {
				if(piece.equals(board.whiteRook1)) {
					int[] correctPos = board.findPiece(board.whiteRook1);
					whiteRook1Pos[0] = (pieceHorizOff + horizOff + (583/8)*correctPos[1]);
					whiteRook1Pos[1] =  (pieceVerticOff + verticOff + (437/8)*correctPos[0]);
					System.out.println(whiteRook1Pos[0] + 59);
				} else if(piece.equals(board.whiteKnight1)) {
					int[] correctPos = board.findPiece(board.whiteKnight1);
					whiteKnight1Pos[0] = (pieceHorizOff + horizOff + (583/8)*(correctPos[1]));
					whiteKnight1Pos[1] =  (pieceVerticOff + verticOff + (437/8)*correctPos[0]);
					System.out.println(whiteKnight1Pos[0]);
				} else if(piece.equals(board.whiteBishop1)) {
					int[] correctPos = board.findPiece(board.whiteBishop1);
					whiteBishop1Pos[0] = (pieceHorizOff + horizOff + (583/8)*(correctPos[1]));
					whiteBishop1Pos[1] =  (pieceVerticOff + verticOff + (437/8)*correctPos[0]);
				} else if(piece.equals(board.whiteQueen)) {
					int[] correctPos = board.findPiece(board.whiteQueen);
					whiteQueenPos[0] = (pieceHorizOff + horizOff + (583/8)*(correctPos[1]));
					whiteQueenPos[1] =  (pieceVerticOff + verticOff + (437/8)*correctPos[0]);
				} else if(piece.equals(board.whiteKing)) {
					int[] correctPos = board.findPiece(board.whiteKing);
					whiteKingPos[0] = (pieceHorizOff + horizOff + (583/8)*(correctPos[1]));
					whiteKingPos[1] =  (pieceVerticOff + verticOff + (437/8)*correctPos[0]);
				} else if(piece.equals(board.whiteBishop2)) {
					int[] correctPos = board.findPiece(board.whiteBishop2);
					whiteBishop2Pos[0] = (pieceHorizOff + horizOff + (583/8)*(correctPos[1]));
					whiteBishop2Pos[1] =  (pieceVerticOff + verticOff + (437/8)*correctPos[0]);
				} else if(piece.equals(board.whiteKnight2)) {
					int[] correctPos = board.findPiece(board.whiteKnight2);
					whiteKnight2Pos[0] = (pieceHorizOff + horizOff + (583/8)*(correctPos[1]));
					whiteKnight2Pos[1] =  (pieceVerticOff + verticOff + (437/8)*correctPos[0]);
				} else if(piece.equals(board.whiteRook2)) {
					int[] correctPos = board.findPiece(board.whiteRook2);
					whiteRook2Pos[0] = (pieceHorizOff + horizOff + (583/8)*(correctPos[1]));
					whiteRook2Pos[1] =  (pieceVerticOff + verticOff + (437/8)*correctPos[0]);
				} else if(piece.equals(board.whitePawn1)) {
					int[] correctPos = board.findPiece(board.whitePawn1);
					whitePawn0Pos[0] = (pieceHorizOff + horizOff + (583/8)*(correctPos[1]));
					whitePawn0Pos[1] =  (pieceVerticOff + verticOff + (437/8)*correctPos[0]);
				} else if(piece.equals(board.whitePawn2)) {
					int[] correctPos = board.findPiece(board.whitePawn2);
					whitePawn1Pos[0] = (pieceHorizOff + horizOff + (583/8)*(correctPos[1]));
					whitePawn1Pos[1] =  (pieceVerticOff + verticOff + (437/8)*correctPos[0]);
				} else if(piece.equals(board.whitePawn3)) {
					int[] correctPos = board.findPiece(board.whitePawn3);
					whitePawn2Pos[0] = (pieceHorizOff + horizOff + (583/8)*(correctPos[1]));
					whitePawn2Pos[1] = (pieceVerticOff + verticOff + (437/8)*correctPos[0]);
				} else if(piece.equals(board.whitePawn4)) {
					int[] correctPos = board.findPiece(board.whitePawn4);
					whitePawn3Pos[0] = (pieceHorizOff + horizOff + (583/8)*(correctPos[1]));
					whitePawn3Pos[1] = (pieceVerticOff + verticOff + (437/8)*correctPos[0]);
				} else if(piece.equals(board.whitePawn5)) {
					int[] correctPos = board.findPiece(board.whitePawn5);
					whitePawn4Pos[0] = (pieceHorizOff + horizOff + (583/8)*(correctPos[1]));
					whitePawn4Pos[1] = (pieceVerticOff + verticOff + (437/8)*correctPos[0]);
				} else if(piece.equals(board.whitePawn6)) {
					int[] correctPos = board.findPiece(board.whitePawn6);
					whitePawn5Pos[0] = (pieceHorizOff + horizOff + (583/8)*(correctPos[1]));
					whitePawn5Pos[1] = (pieceVerticOff + verticOff + (437/8)*correctPos[0]);
				} else if(piece.equals(board.whitePawn7)) {
					int[] correctPos = board.findPiece(board.whitePawn7);
					whitePawn6Pos[0] = (pieceHorizOff + horizOff + (583/8)*(correctPos[1]));
					whitePawn6Pos[1] = (pieceVerticOff + verticOff + (437/8)*correctPos[0]);
				}  else if(piece.equals(board.whitePawn8)) {
					int[] correctPos = board.findPiece(board.whitePawn8);
					whitePawn7Pos[0] = (pieceHorizOff + horizOff + (583/8)*(correctPos[1]));
					whitePawn7Pos[1] = (pieceVerticOff + verticOff + (437/8)*correctPos[0]);
				}
			}
			
		}

	}
	

}
