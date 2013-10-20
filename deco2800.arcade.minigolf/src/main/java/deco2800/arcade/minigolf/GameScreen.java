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

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.audio.Music;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import java.net.URL;
import java.io.File;
import javax.sound.sampled.Clip;

import java.util.ArrayList;

/**
 * GameScreen is the main game loop of minigolf.
 * It calls all render and update methods from other classes. 
 * It also handles input from user. 
 */

public class GameScreen implements Screen, InputProcessor {
	
	private MiniGolf golf; 
	private World world;
	private WorldRenderer renderer; 
	private WorldController wControl;
	private BallController ballCont;
	
	@SuppressWarnings("unused")
	private int width, height, totalShots;
	private int level, scoreX, scoreY; //hole
	private float power, fadeInOut, fadeVar;
	private boolean scoreYes;
	boolean gamePaused;
	
	//Variables for the button
	private BitmapFont font1, font2, font3;
	private String holeShots, gameShots, totalScore;
	private ArrayList<Integer> scoreCard;
	private Stage stage;
	private TextureAtlas butAtlas;
	private Skin butSkin;
	private Texture scoreCardTexture, bgTexture;
	private SpriteBatch butBatch, scoreBatch;
	private Sprite scoreCardSprite;

	private TextButton mainButton, resetButton;
	private int disposeCount = 0;
	
	public GameScreen(MiniGolf game, int hole){
		this.scoreCard = new ArrayList<Integer>();
		this.golf = game;
		this.level = hole;
		this.fadeInOut = 0;
		this.fadeVar = 0.0001f;
		gamePaused = false;
		playMusic();		
	}
	
	/* get and set current level */
	private int getLevel() {
		golf.incrementAchievement("minigolf.CompFirstMap");
		return level; 
	}
	private void setLevel(int level) {
		this.level = level; 
	}
	
	@Override 
	public void show() { 
			Texture.setEnforcePotImages(false);
			
			try { //opening a file in world, need to catch exceptions
				world = new World(level);
			} catch (Exception e) {
				System.err.println("An error has occured while opening a level text file");
			} 
			//create and display the current level
			renderer = new WorldRenderer(world, false, this.level, scoreCard); //render objects 
			wControl = new WorldController(world, this.level, scoreCard); //initialise controller
			wControl.setHole(level); //set current hole 
			ballCont = new BallController(world); //initialise controller
			
			// load score card image and set it's position when displayed
			scoreBatch = new SpriteBatch();
			scoreCardTexture = new Texture("scoreCard.png");
			scoreCardSprite = new Sprite(scoreCardTexture);
			scoreCardSprite.setX(Gdx.graphics.getWidth()/2 - scoreCardSprite.getWidth()/2);
			scoreCardSprite.setY(Gdx.graphics.getHeight()/2 - scoreCardSprite.getHeight()/2);
			
			//background image for pause screen between levels
			bgTexture = new Texture("background1.png");
			
			//Menu and Reset Button code + font
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
			endOfHole();
			
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
	
	/* enables viewing of the score-card 
	 * displays the scores across all playable holes, and the total score
	 */
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
	
	@Override /* sets the size of screen and adjusts button size and handles button presses */
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
		
		
		//menu action on click
		mainButton.addListener(new InputListener(){
			public boolean touchDown(InputEvent event, float x, float y, int pointer,int button){				
				return true;
			}			
			public void touchUp(InputEvent event, float x, float y, int pointer, int button){
				golf.create();					
			}			
			
		});
		//reset action on click
		resetButton.addListener(new InputListener(){
			public boolean touchDown(InputEvent event, float x, float y, int pointer,int button){			
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
	/* playus the background music while in-game */
	private void playMusic(){	
		URL path = this.getClass().getResource("/");
		try {
			System.out.println("path: \n\n" + path.toString());
			String resource = path.toString().replace(".arcade/build/classes/main/", 
			".arcade.minigolf/src/main/").replace("file:", "") + 
			"resources/newHero.wav";
			System.out.println(resource);
			File file = new File(resource);
			new FileHandle(file);
			AudioInputStream audioIn = AudioSystem.getAudioInputStream(file);
			Clip clip = AudioSystem.getClip();
			clip.open(audioIn);
			clip.start();
			clip.loop(Clip.LOOP_CONTINUOUSLY);

		} catch (Exception e) {
			e.printStackTrace();
		}			
	
	}
	
	/* contains a list of progressive achievements that can be obtained while playing */
	private void progressAchievements(int nextHole){
		switch(nextHole){
			case 1:
			golf.incrementAchievement("minigolf.9hole.level1");
			break;
			case 2:
			golf.incrementAchievement("minigolf.9hole.level2");
			break;
			case 3:
			golf.incrementAchievement("minigolf.9hole.level3");
			break;
			case 4:
			golf.incrementAchievement("minigolf.9hole.level4");
			break;
			case 5:
			golf.incrementAchievement("minigolf.9hole.level5");
			break;
			case 6:
			golf.incrementAchievement("minigolf.9hole.level6");
			break;
			case 7:
			golf.incrementAchievement("minigolf.9hole.level7");
			break;
			case 8:
			golf.incrementAchievement("minigolf.9hole.level8");
			break;
			case 9:
			golf.incrementAchievement("minigolf.9hole.level9");
			break;
			}
	}
	/* When player sinks the ball, displays scorecard until a button is pressed*/
	public void endOfHole(){
		scoreYes = true; //enable scorecard	
		int nextHole = (getLevel() + 1);  //get the next hole
		if(nextHole == 19){ //only render up to level 18
			if(Gdx.input.isButtonPressed(Buttons.RIGHT)){
			this.scoreCard = new ArrayList<Integer>();
			totalScore = "";
			totalShots = 0; 
			golf.create();
			}
		} else {
			setLevel(nextHole); //set the next hole
			golf.setScreen(golf.hole, level); //render next hole	
		}
		//enable achievement if player gets a hole in one
		if(totalShots == 1){
			golf.incrementAchievement("minigolf.marksman");
		}		
		//update progressive achievements
		progressAchievements(nextHole);
	}
		
	
	@Override 
	public void hide() { 
		Gdx.input.setInputProcessor(null);		
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
		if (disposeCount == 1) {
			return;
		}
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
