package deco2800.arcade.minigolf;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;

import java.util.ArrayList;

/* Gamescreen is the main game loop of minigolf.
 * It calls all render and update methods from other classes 
 * It also handles input from user */

public class GameScreen implements Screen, InputProcessor {
	
	MiniGolf golf; 
	private World world;
	private WorldRenderer renderer; 
	private WorldController wControl;
	private BallController ballCont;
	
	@SuppressWarnings("unused")
	private int width, height, totalShots;
	public int level, scoreX, scoreY; //hole
	private float power, fadeInOut, fadeVar;
	private boolean scoreYes, gamePaused;
	
	//Variables for the button
	BitmapFont font1, font2, font3;
	String holeShots, gameShots, totalScore;
	ArrayList<Integer> scoreCard;
	Stage stage;
	TextureAtlas butAtlas;
	Skin butSkin;
	Texture scoreCardTexture, bgTexture;
 	SpriteBatch butBatch, scoreBatch;
	Sprite scoreCardSprite;

	TextButton mainButton, resetButton;
	int disposeCount = 0;
	
	public GameScreen(MiniGolf game, int hole){
		this.scoreCard = new ArrayList<Integer>();
		this.golf = game;
		this.level = hole;
		this.fadeInOut = 0;
		this.fadeVar = 0.0001f;
		gamePaused = false;
		System.out.println("hole num: "+this.level);
	}
	
	/* get and set current level */
	private int getLevel() {
		return level; 
	}
	private void setLevel(int level) {
		this.level = level; 
	}
	
	@Override 
	public void show() { 
			Texture.setEnforcePotImages(false);
			try { //opening a file in world so catch exceptions
				world = new World(level);
			} catch (Exception e) {
				e.printStackTrace();
			} //create hole 
			renderer = new WorldRenderer(world, false, this.level, scoreCard); //render objects 
			wControl = new WorldController(world, this.level, scoreCard); //initialise controller
			wControl.setHole(level); //set current hole 
			ballCont = new BallController(world); //initialise controller
			
			// score card
			scoreBatch = new SpriteBatch();
			scoreCardTexture = new Texture("scoreCard.png");
			scoreCardSprite = new Sprite(scoreCardTexture);
			scoreCardSprite.setX(Gdx.graphics.getWidth()/2 - scoreCardSprite.getWidth()/2);
			scoreCardSprite.setY(Gdx.graphics.getHeight()/2 - scoreCardSprite.getHeight()/2);
			
			//background image for pause screen between levels
			bgTexture = new Texture("background1.png");
			
			//button code
			butBatch = new SpriteBatch();
			butAtlas = new TextureAtlas("button.pack");
			butSkin = new Skin();
			butSkin.addRegions(butAtlas);
			font1 = new BitmapFont(Gdx.files.internal("font_black.fnt"),false);
			font2 = new BitmapFont(Gdx.files.internal("font_white.fnt"),false);
			font3 = new BitmapFont(Gdx.files.internal("scoreFont.fnt"),false);
	}
	
	@Override //continuously render all objects
	public void render(float delta) { 
		//if a hole has been completed display score
		if(scoreYes){
			fadeInOut = 1f;
			fadeVar = 0.0001f;
			this.pause();
		} else {
		if(this.level != wControl.getHole()){ //if ball is in hole						
			scoreYes = true;	
			//set next level
			int nextHole = (getLevel() + 1); 
			if(nextHole == 19){
				if(Gdx.input.isButtonPressed(Buttons.RIGHT)){
				this.scoreCard = new ArrayList<Integer>();
				totalScore = "";
				totalShots = 0; 
				golf.create();
				}
			} else {
				setLevel(nextHole); //set the next hole
				System.out.println("level is: "+ this.level); 
				golf.setScreen(golf.hole, level); //render next hole	
			}
		}
		//clear everything on screen
		Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		//render everything and apply updates
		ballCont.update();
		renderer.render();		
		capPower();
		//ballCont.update();
		wControl.update(delta, this.power, renderer.getDir());
		ballCont.update();
		
		//score and shot info
		LabelStyle labelStyle = new LabelStyle();
		labelStyle.font = font2;
		
		holeShots = "Shots: " + wControl.getNumShots();
		totalShots += wControl.getHoleShots();
		totalScore = " " + totalShots;
		
		//if end of level, display score-card
		//displayScoreCard();
		
		//Button code
		stage.act(delta);		
		butBatch.begin();
		stage.draw();
		butBatch.end();
		}
		
	}
	/* cap the speed that the player can hit the ball at */
	private void capPower(){
		this.power = renderer.getPower(); //get the power
		this.power *= 1.5;
		
	}
	
	/* checks if the hole is over, if so fade in and out the score-card */
	private void displayScoreCard(){
		if(scoreYes){
			//gamePaused = true;
			scoreX = (int)scoreCardSprite.getX() + 71;
			scoreY = (int)scoreCardSprite.getY() + 112;
			scoreBatch.begin();

			scoreBatch.draw(bgTexture, 0, 0);
			scoreCardSprite.setColor(1, 1, 1, fadeInOut);
			scoreCardSprite.draw(scoreBatch);
			
			
			for(int i=0; i<scoreCard.size(); i++){
				if(i == 9){
					scoreX = (int)scoreCardSprite.getX() + 73;
					scoreY -= 54;
				}
				
				font3.setColor(0, 0, 0, fadeInOut);
				font3.draw(scoreBatch, scoreCard.get(i).toString(), scoreX, scoreY);
				font3.draw(scoreBatch, totalScore, (int)scoreCardSprite.getX() + 210,
					     (int)scoreCardSprite.getY() + 26);
				font3.setColor(Color.WHITE);
				font3.draw(scoreBatch, "Right Click to continue", 525, 650);
				scoreX += 30 + ((i%9)*0.5);
			}
			
			scoreBatch.end();
		}
	}
	
	@Override 
	public void resize(int width, int height) { 
		renderer.setSize(width, height);
		this.width = width; 
		this.height = height; 
		
		//button code
		if(stage == null){
			stage = new Stage(width,height,true);
		}
		stage.clear();
		InputMultiplexer inpMult = new InputMultiplexer(stage);
		inpMult.addProcessor(stage);
		inpMult.addProcessor(this);
		Gdx.input.setInputProcessor(inpMult);
		
		TextButtonStyle butStyle = new TextButtonStyle();
		butStyle.up = butSkin.getDrawable("butdown");
		butStyle.down = butSkin.getDrawable("butup");
		butStyle.fontColor = Color.BLACK;
		butStyle.font = font1;
		butStyle.font.setScale(0.5f, 0.5f);
		
		mainButton = new TextButton("Menu", butStyle);
		resetButton = new TextButton("Reset", butStyle);
		
		mainButton.setWidth(100);
		mainButton.setHeight(25);
		mainButton.setX(510);
		mainButton.setY(680);
		
		resetButton.setWidth(100);
		resetButton.setHeight(25);
		resetButton.setX(670);
		resetButton.setY(680);
		
		
		//menu
		mainButton.addListener(new InputListener(){
			public boolean touchDown(InputEvent event, float x, float y, int pointer,int button){
				System.out.println("down");				
				return true;
			}			
			public void touchUp(InputEvent event, float x, float y, int pointer, int button){
				golf.create();					
			}			
			
		});
		//reset
		resetButton.addListener(new InputListener(){
			public boolean touchDown(InputEvent event, float x, float y, int pointer,int button){
				System.out.println("down");				
				return true;
			}
			
			public void touchUp(InputEvent event, float x, float y, int pointer, int button){
				int curLevel = getLevel(); 
				golf.setScreen(golf.hole, curLevel);					
			}
			
			
		});
		
		
		stage.addActor(mainButton);
		stage.addActor(resetButton);
		
 
	
	}
	
	
	
	@Override 
	public void hide() { 
		Gdx.input.setInputProcessor(null);
		//dispose();
		
	}
	
	@Override 
	public void pause() {	
		scoreYes = true;
		gamePaused = true;
		Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		//SpriteBatch batcher = new SpriteBatch();
		//Texture a = new Texture(Gdx.files.internal("resources/background1.png"));
		//batcher.begin();
		//batcher.draw(a, 0,0);
		displayScoreCard();
		if(Gdx.input.isButtonPressed(Buttons.RIGHT)){
			//batcher.end();			
			scoreYes = false;
			gamePaused = false;
		}				
	}
	
	@Override 
	public void resume() { 
		
	}
	
	@Override 
	public void dispose() { 
		Gdx.input.setInputProcessor(null);
		
		//button code
		if (disposeCount == 1) return;
		butBatch.dispose();
		butAtlas.dispose();
		font1.dispose();		
		butSkin.dispose();
		stage.dispose();
		
		renderer.dispose();
		
	}
	
	public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor){
		if (stage.getActors().contains(fromActor, true)){
			Gdx.input.setInputProcessor(stage);
		}
	}	
	
	public void exit(InputEvent event,	float x, float y,int pointer, Actor toActor){
		if (stage.getActors().contains(toActor, true)){
			Gdx.input.setInputProcessor(this);
		}		
	}	

	//a key from keyboard is pressed
	@Override
	public boolean keyDown(int keycode) {
		if(keycode == Keys.LEFT)
			wControl.leftKeyPressed();
		return true;
	}

	@Override
	public boolean keyTyped(char arg0) {
		return false;
	}

	//a key from keyboard is released
	@Override
	public boolean keyUp(int keycode) {
		return false;
	}

	@Override
	public boolean mouseMoved(int arg0, int arg1) {
		return false;
	}

	@Override
	public boolean scrolled(int arg0) {
		return false;
	}

	//mouse click pressed
	@Override
	public boolean touchDown(int arg0, int arg1, int arg2, int button) {
		if(button == Buttons.LEFT){
			wControl.leftKeyPressed();
		}
		return true;
	}
	
	//mouse click released
	@Override
	public boolean touchUp(int arg0, int arg1, int arg2, int button) {
		if(button == Buttons.LEFT && gamePaused != true){
			wControl.leftKeyReleased();
		}
		return true;
	}


	@Override
	public boolean touchDragged(int arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub
		return false;
	}

	
}
