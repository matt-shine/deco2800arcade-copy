package deco2800.arcade.chess;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.math.Vector2;

import deco2800.arcade.client.network.NetworkClient;
import deco2800.arcade.model.Player;



public class PlayScreen implements Screen
{
	private Chess game;
	
	private OrthographicCamera camera;
	private Stage stage;
	private Board board;
	private NetworkClient networkClient;	
	private Player player;

	public PlayScreen(Chess game){
		this.game = game;
	}	
	 
    @Override
    public void show()
    {
    	// Initialising variables
		this.stage = new Stage( Chess.SCREENWIDTH, Chess.SCREENHEIGHT, true);

		// Setting up the camera view for the game
		camera = (OrthographicCamera) stage.getCamera();
    	camera.setToOrtho(false, Chess.SCREENWIDTH, Chess.SCREENHEIGHT);
    	camera.update();
    	game.initPiecePos();
    	board = new Board();
		game.movePieceGraphic();
    	game.drawPieces();
    	

    }
    
    @Override
    public void hide() {
    	stage.dispose();
    } 
    
    @Override
    public void render(float delta)
    {   
  
    	//Gdx.gl.glClearColor(0, 0, 0, 1);
		//Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
    
		stage.draw();

    }

    
    @Override
    public void resize(int width, int height) {
    }
    
    @Override
    public void pause() {
    }
    
    @Override
    public void resume() {
    }
    
    @Override
    public void dispose() {
    }
}
