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
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
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
public class Chess extends GameClient implements MouseListener{

	boolean players_move;
	boolean playing;
	Piece piece = new King(false);
	private String[] players = new String[2]; // The names of the players: the local player is always players[0]
	
	private OrthographicCamera camera;
	
	public static final int SCREENHEIGHT = 600;
	public static final int SCREENWIDTH = 900;
	
	private ShapeRenderer shapeRenderer;
	private SpriteBatch batch;
	private BitmapFont font;

	private String statusMessage;
	
	private Texture chessBoard, blackBishop, blackBishop2, blackRook, 
	blackRook2, blackKnight, blackKnight2, blackKing, blackQueen, 
	blackPawn0, blackPawn1, blackPawn2, blackPawn3, blackPawn4, blackPawn5, blackPawn6, blackPawn7, 
	whiteBishop2, whiteBishop, whiteRook, whiteRook2, whiteKnight, whiteKnight2, whiteKing, whiteQueen, 
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
		Board board = new Board();
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
		camera.setToOrtho(false, SCREENWIDTH, SCREENHEIGHT);
		System.out.println("Screen width is" + " " +SCREENWIDTH);
		System.out.println("Screen height is" + " " +SCREENHEIGHT);
		
		//Necessary for rendering
		shapeRenderer = new ShapeRenderer();
		font = new BitmapFont();
		font.setScale(2);
		batch = new SpriteBatch();
		
		Texture.setEnforcePotImages(false);
	
		
		/////fixed/////
		// load the images for the droplet and the bucket, 512x512 pixels each
		//texture = new Texture(Gdx.files.classpath("srcPictures/chessboard.png"));
		chessBoard = new Texture(Gdx.files.classpath("imgs/chessboard.png"));
		
		
		//Pieces
		blackBishop = new Texture(Gdx.files.classpath("imgs/Black B.png"));
		blackRook = new Texture(Gdx.files.classpath("imgs/Black R.png"));
		blackKnight = new Texture(Gdx.files.classpath("imgs/Black N.png"));
		blackKing = new Texture(Gdx.files.classpath("imgs/Black K.png"));
		blackQueen = new Texture(Gdx.files.classpath("imgs/Black Q.png"));
		blackPawn0 = new Texture(Gdx.files.classpath("imgs/Black P.png"));
		whiteBishop = new Texture(Gdx.files.classpath("imgs/White B.png"));
		whiteRook = new Texture(Gdx.files.classpath("imgs/White R.png"));
		whiteKnight = new Texture(Gdx.files.classpath("imgs/White N.png"));
		whiteKing = new Texture(Gdx.files.classpath("imgs/White K.png"));
		whiteQueen = new Texture(Gdx.files.classpath("imgs/White Q.png"));
		whitePawn0 = new Texture(Gdx.files.classpath("imgs/White P.png"));
		whiteRook2 = whiteRook;
		whiteBishop2= whiteBishop;
		whiteKnight2 = whiteKnight;
		blackBishop2 = blackBishop;
		blackKnight2 = blackKnight;
		blackRook2 = blackRook;
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
	    batch.draw(chessBoard, 1, 1);
	    
	    
	    //black pieces
	    batch.draw(blackRook, 25, 435);
	    batch.draw(blackKnight, 145,435 );	
	    batch.draw(blackBishop, 85, 435);
	    batch.draw(blackQueen, 205, 435);
	    batch.draw(blackKing, 260, 435);
	    batch.draw(blackBishop2, 320, 435);	
	    batch.draw(blackKnight2, 380, 435);
	    batch.draw(blackRook2, 435, 435);
	    
	    //whitepieces
	    //Batch.draw(piece, lengthpos, heightpos)
	    batch.draw(whiteRook, 25, 25);
	    batch.draw(whiteBishop, 145, 25 );	
	    batch.draw(whiteKnight, 85, 25);
	    batch.draw(whiteQueen, 205, 25);
	    batch.draw(whiteKing, 260, 25);
	    batch.draw(whiteBishop2, 320, 25);	
	    batch.draw(whiteKnight2, 380, 25);
	    batch.draw(whiteRook2, 435, 25);
	    
	    
	    //pawns white
	    batch.draw(whitePawn0, 25, 90);
	    batch.draw(whitePawn1, 145, 90);	
	    batch.draw(whitePawn2, 85, 90);
	    batch.draw(whitePawn3, 205, 90);
	    batch.draw(whitePawn4, 260, 90);
	    batch.draw(whitePawn5, 320, 90);	
	    batch.draw(whitePawn6, 380, 90);
	    batch.draw(whitePawn7, 435, 90);
	    //pawns black
	    batch.draw(blackPawn0, 25, 380);
	    batch.draw(blackPawn1, 145, 380);	
	    batch.draw(blackPawn2, 85, 380);
	    batch.draw(blackPawn3, 205, 380);
	    batch.draw(blackPawn4, 260, 380);
	    batch.draw(blackPawn5, 320, 380);	
	    batch.draw(blackPawn6, 380, 380);
	    batch.draw(blackPawn7, 435, 380);
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



	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	public void paint(Graphics g){
		
		
	}
	

}
