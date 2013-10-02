package deco2800.arcade.lunarlander;

import java.util.*;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.PolygonSprite;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.Input.Keys;

import deco2800.arcade.model.Game;
import deco2800.arcade.model.Game.ArcadeGame;
import deco2800.arcade.model.Player;
import deco2800.arcade.client.GameClient;
import deco2800.arcade.client.network.NetworkClient;


@ArcadeGame(id="LunarLander")
public class LunarLander extends GameClient {
		
	private OrthographicCamera camera;
	public static final int SCREENHEIGHT = 800;
	public static final int SCREENWIDTH = 1200;
	
	private SpriteBatch batch;
	private ShapeRenderer shapeRenderer; // draws shapes 
	private Texture landerTexture;
	private Texture backgroundTexture;
	private Texture moonTexture;
	private TextureRegion backgroundTextureRegion; // draws portion of background png file
	private TextureRegion moonTextureRegion;
	private BitmapFont font; // draws text 
	//Texture[] backgroundTextures;
	
	// lander constants
	private float landerX; // the width of the lander png
	private float landerY; // the height of the lander png
	private float initialPositionX; // the initial position of the lander - x coordinate
	private float initialPositionY; // the initial postiion of the lander - y coordinate
	
	
	//terrain generation
	private List<List<Integer>> terrain;
	private boolean randomMap;
	
	PolygonSprite poly;
	PolygonSpriteBatch polyBatch;
	Texture textureSolid;

	
	// statistics to be displayed on game screen
	private int score;
	private int fuel;
	private int speed;
	private int time;
	
	// speed of movement when lander is moved left or right 
	private float sideSpeed = 40.0f;
	// speed of movement when lander is moved downwards
	private float downwardSpeed = 80.0f;
	// speed of movement when lander is moving downwards of its own accord
	private float downwardDrift = 20.0f;
	
	private NetworkClient networkClient;
	
	/**
	 * Basic constructor for Lunar Lander 
	 * @param player The name of the player
	 * @param networkClient The network client for sending/receiving messages to/from the server
	 */
	
	public LunarLander(Player player, NetworkClient networkClient) {
		super(player, networkClient);
        this.networkClient = networkClient;  
	}
	
	
	/**
	 * Creates the game
	 */
	@Override
	public void create() {
			
		// setting up various Gdx tools
		batch = new SpriteBatch();
		// loads background png file
		backgroundTexture = new Texture(Gdx.files.internal("lunarlanderassets/rose_nebula.png"));
		// creates a portion of that background to display
		backgroundTextureRegion = new TextureRegion(backgroundTexture, 1200, 800);
		moonTexture = new Texture(Gdx.files.internal("lunarlanderassets/moon.png"));
		moonTextureRegion = new TextureRegion(moonTexture, 200, 200);
		// loads lander png file
		landerTexture = new Texture(Gdx.files.internal("lunarlanderassets/lander.png"));
		// creates a new font to use for text
		font = new BitmapFont();
		shapeRenderer = new ShapeRenderer();

		camera = new OrthographicCamera();
		camera.setToOrtho(false, SCREENWIDTH, SCREENHEIGHT);
		
		// setting up various constants 
		initialPositionY = 750;
		initialPositionX = 600;
		landerX = 30;
		landerY = 30;
		speed = 1;
		
		randomMap = true;
		terrain = createMap();
	}

	/**
	 * Render the current state of the game and process updates
	 */
	@Override
	public void render() {
		
		// move lander left
		if ((Gdx.input.isKeyPressed(Keys.A)) || (Gdx.input.isKeyPressed(Keys.LEFT))) { 
			initialPositionX -= Gdx.graphics.getDeltaTime() * sideSpeed;
		}
		
		// move lander right
	    if ((Gdx.input.isKeyPressed(Keys.D)) || (Gdx.input.isKeyPressed(Keys.RIGHT))) {
	    	initialPositionX += Gdx.graphics.getDeltaTime() * sideSpeed;
	    }
		
	    // boost the lander's speed
		if ((Gdx.input.isKeyPressed(Keys.W)) || (Gdx.input.isKeyPressed(Keys.UP))) {
			initialPositionY += (Gdx.graphics.getDeltaTime() * downwardSpeed)/2;
		}
		
		//for debugging purposes
		if (Gdx.input.isKeyPressed(Keys.Q)) {
			System.out.println("debug button has been pressed!");
		}
		
		// clear screen, create background using texture
	    Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
	    batch.begin();
	    batch.draw(backgroundTextureRegion, 0, 0);
	    batch.draw(landerTexture, initialPositionX, initialPositionY, landerX, landerY);    
	    
	    // display values on screen
	    font.setColor(Color.WHITE);
	    font.draw(batch, "Score: " + Integer.toString(score), SCREENWIDTH - 200, SCREENHEIGHT - 40);
	    font.draw(batch, "Remaining fuel: " + Integer.toString(fuel), SCREENWIDTH - 200, SCREENHEIGHT - 60);
	    font.draw(batch, "Current speed: " + Integer.toString(speed), SCREENWIDTH - 200, SCREENHEIGHT - 80);
	    font.draw(batch, "Time spent: " + Integer.toString(time), SCREENWIDTH - 200, SCREENHEIGHT - 100);
	    
	    TextureRegion moonPattern = new TextureRegion(moonTexture, 0, 0, 200, 200);
	    batch.draw(moonPattern, 500, 500);
	    
	    batch.end();

	    camera.update();
	    
	    shapeRenderer.setProjectionMatrix(camera.combined);
	    batch.setProjectionMatrix(camera.combined);
	    
	    //Begin drawing of shapes
	    shapeRenderer.begin(ShapeType.Line);
	    Gdx.gl.glLineWidth(5);
	    
	    //set map to be randomonly made, or not
	    if(randomMap == true){
	    for (int i = 0; i < terrain.size(); i++){
	    	shapeRenderer.setColor(5, 255, 5, 1);
	    	shapeRenderer.line(terrain.get(i).get(0), terrain.get(i).get(1), terrain.get(i).get(2), terrain.get(i).get(3));
	    	
	    }
	    }else{
	    	//load premade ArrayList of points, and background texture
	    }
	    
	    //colors the landing pad green
	    shapeRenderer.setColor(255, 0, 0, 1);
	    shapeRenderer.line(terrain.get(0).get(0), terrain.get(0).get(1), terrain.get(0).get(2), terrain.get(0).get(3));
	    shapeRenderer.setColor(1, 1, 1, 1);
	    shapeRenderer.end();
	    
	    //fills map with rectangles
	    for (int i = 1; i < terrain.size(); i++){
	    	if (terrain.get(i).get(1) > terrain.get(i).get(3)){
	    		shapeRenderer.begin(ShapeType.FilledRectangle);
			    shapeRenderer.setColor(1, 1, 1, 1);
				shapeRenderer.filledRect(terrain.get(i).get(0), 0, terrain.get(i).get(2) - terrain.get(i).get(0), terrain.get(i).get(3));
				shapeRenderer.end();
	    	}else if (terrain.get(i).get(1) < terrain.get(i).get(3)){
	    		shapeRenderer.begin(ShapeType.FilledRectangle);
			    shapeRenderer.setColor(1, 1, 1, 1);
				shapeRenderer.filledRect(terrain.get(i).get(0), 0, terrain.get(i).get(2) - terrain.get(i).get(0), terrain.get(i).get(1));		
				shapeRenderer.end();
	    	}
	  
	    }
	    
	    //fills underneath landing pad
	    shapeRenderer.begin(ShapeType.FilledRectangle);
	    shapeRenderer.setColor(1, 1, 1, 1);
		shapeRenderer.filledRect(terrain.get(0).get(0), 0, terrain.get(0).get(2), terrain.get(0).get(3));		
		shapeRenderer.end();
	    
	    for (int i = 1; i < terrain.size(); i++){
	    	if (terrain.get(i).get(1) > terrain.get(i).get(3)){
		    	shapeRenderer.begin(ShapeType.FilledTriangle);
			    shapeRenderer.setColor(88, 222, 1, 1);
				shapeRenderer.filledTriangle(terrain.get(i).get(0), terrain.get(i).get(1), terrain.get(i).get(2), terrain.get(i).get(3), terrain.get(i).get(0), terrain.get(i).get(3));		
				shapeRenderer.end();
	    	}else if (terrain.get(i).get(1) < terrain.get(i).get(3)){
	    		shapeRenderer.begin(ShapeType.FilledTriangle);
			    shapeRenderer.setColor(88, 222, 1, 1);
				shapeRenderer.filledTriangle(terrain.get(i).get(0), terrain.get(i).get(1), terrain.get(i).get(2), terrain.get(i).get(3), terrain.get(i).get(2), terrain.get(i).get(1));		
				shapeRenderer.end();
	    	}
	    }
	    
	    /*if(!(initPosition + finalY <= 20) && moving == true){
	    	
	    case INPROGRESS: //Point is underway, ball is moving
	    	
	    case GAMEOVER: //The game has been won, wait to exit
	    	if (Gdx.input.isTouched()) {
	    		gameOver();
	    		ArcadeSystem.goToGame(ArcadeSystem.UI);
	    	}
	    	break;
	    }*/
	    
	    //very basic downwards movement of the lander
	    if(!((initialPositionY - 0.25) < 20)) {
	    	initialPositionY -= 0.25;
	    }
	    
		super.render();
		
	}
	
	public List<List<Integer>> createMap(){
		
		List<List<Integer>> terrain = new ArrayList<List<Integer>>();
		
		//creating the landing pads coordinates
		int landPadLeftX = randNum(100, 700);
		int landPadLeftY = randNum(50, 200);
		int landPadRightX = landPadLeftX + 50; //makes the pad 50 pixels wide
		int landPadRightY = landPadLeftY;
		
		//adding the landPad as the first element of the array, TO DO, change color of landing pad
		terrain.add(new ArrayList<Integer>());
		terrain.get(0).add(landPadLeftX);
		terrain.get(0).add(landPadLeftY);
		terrain.get(0).add(landPadRightX);
		terrain.get(0).add(landPadRightY);
		
		int pointX = terrain.get(0).get(2);
		int pointY = terrain.get(0).get(3);
		int arrayPosition = 1;
		
		//automatically adds lines to the right of the landing pad
		while (pointX < 1300){

			int newPointX = randNum(50, 200);
			int newPointY = randNum(100, 400);
			
			terrain.add(new ArrayList<Integer>());
			terrain.get(arrayPosition).add(pointX);
			terrain.get(arrayPosition).add(pointY);
			terrain.get(arrayPosition).add(newPointX + pointX);
			terrain.get(arrayPosition).add(newPointY);
			
			pointX = pointX + newPointX;
			pointY = newPointY;
			arrayPosition++;
			
		}
		
		int pointX2 = terrain.get(0).get(0);
		int pointY2 = terrain.get(0).get(1);
		int arrayPosition2 = arrayPosition;
		
		//adds lines to the left of the platform/landing pad
		while (pointX2 > -100){

			int newPointX2 = randNum(50, 200);
			int newPointY2 = randNum(100, 400);
			
			terrain.add(new ArrayList<Integer>());
			terrain.get(arrayPosition2).add(pointX2 - newPointX2);
			terrain.get(arrayPosition2).add(newPointY2);
			terrain.get(arrayPosition2).add(pointX2);
			terrain.get(arrayPosition2).add(pointY2);
			
			pointX2 = pointX2 - newPointX2;
			pointY2 = newPointY2;
			arrayPosition2++;
			
			
		}
		
		
		return terrain;
	}
	
	//creates a random length of a line, used by createMap()
	public int randNum(int start, int finish){
		int number = start + new Random().nextInt(finish - start + 1 );
		return number;
	}
	
	@Override
	public void resize(int arg0, int arg1) {
		super.resize(arg0, arg1);
	}

	@Override
	public void resume() {
		super.resume();
	}

	
	private static final Game game;
	static {
		game = new Game();
		game.id = "lunarlander";
		game.name = "LunarLander";
        game.description = "Can your Lunar Lander make it to the surface safely?";
	}
	
	public Game getGame() {
		return game;
	}
	
}
