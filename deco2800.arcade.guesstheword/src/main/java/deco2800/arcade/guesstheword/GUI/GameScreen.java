package deco2800.arcade.guesstheword.GUI;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

import deco2800.arcade.guesstheword.gameplay.GameModel;
import deco2800.arcade.guesstheword.gameplay.WordShuffler;

public class GameScreen implements Screen, InputProcessor {
	/**
	 * GuessTheWord instance
	 * */
	private GuessTheWord game;
	/**
	 * Skin instance
	 * */
	private Skin skin;
	/**
	 * SpriteBatch instance
	 * */
	private SpriteBatch batch;
	/**
	 * Stage instance
	 * */
	private Stage stage;
	
	/**
	 * WordShuffler instance
	 * */
	private WordShuffler word;
	/**
	 * GameModel instance
	 * */
	private GameModel gm;
	
	/**
	 * InputMultxplexer 
	 * */
	private InputMultiplexer inputMultiplexer = new InputMultiplexer(this);
	
	// The different panels for the game screen 
	/**
	 * ButtonPanel instance
	 * */
	private ButtonPanel buttonPanel;
	/**
	 * PicturePanel instance
	 * */
	private PicturePanel picturePanel;
	/**
	 * RightPanel instance
	 * */
	private RightPanel rightPanel;
	/**
	 * LeftPanel instance
	 * */
	private LeftPanel leftPanel;


	// The letters buttons for the games
	/**
	 * TextButton use for the letters. 
	 * */
	private TextButton button1, button2, button3, button4, button5, 
	button6	, button7, button8, button9, button10 ;
	/**
	 * Back Button. 
	 * */
	private TextButton  backButton ;
	/**
	 * Play Button. 
	 * */
	private TextButton playButton;

	// The letters textfield for the game
	/**
	 * Textfields for the letters
	 * */
	public TextField textfield0, textfield1, textfield2, textfield3, 
					  textfield4, textfield5, textfield6;  
	/**
	 *Score Label
	 * */
	public Label scoreLabel; 
	/**
	 * Level Label
	 * */
	public Label levelLabel;
	/**
	 * Category Label 
	 * */
	private Label categoryLabel;
	
	/**
	 * LabelStyle for the labels
	 * */
	private LabelStyle labelStyle;
	
	/**
	 * ScrollPane for category window.
	 * */
	private ScrollPane scrollpane;
	
	/**
	 * A list to store the category played
	 * */
	private ArrayList<String> catList;
	 
	/**
	 * To store the timer allowance for each different level
	 * */
    private int gameTime = 0;
    /**
	 * Texture for storing the background image.
	 * */
	private Texture backGroudTexture;
	

	/**
	 * Array of keys code for Letters (A-Z)
	* */	
	private String[] lettersCode = {"A" ,"B" ,"C"  , "D" , "E" , "F" , "G" , "H"  ,"I"  ,"J" , "K"  , "L"  
			,"M" , "N", "O" ,"P", "Q", "R" ,"S" ,"T", "U", "V", "W", "X", "Y", "Z"};
	
	//PUBLC VARIABLES
	public HashMap<String, HashMap<String, Texture>> hm;
	public ArrayList<TextButton> buttonList;

	public GameScreen(){
	}
	
	GameScreen(final GuessTheWord game){
		this.game = game;
		this.skin = game.skin;
		batch =  new SpriteBatch();
		stage =  new Stage();

		//Creating Instances of the wordshuffler class 
		word =  new WordShuffler();
		
		//Create an instance of GameModel
		gm =  new GameModel(game, this, word);

		hm = new HashMap<String, HashMap<String, Texture>>();
		buttonList = new ArrayList<TextButton>();
		catList = new ArrayList<String>();
	}
	
	
	@Override
	public void render(float arg0) {
		Gdx.gl.glClearColor( 0f, 0f, 0f, 1f );
        Gdx.gl.glClear( GL20.GL_COLOR_BUFFER_BIT );

        // Draw the background 
        batch.begin();
		batch.draw(backGroudTexture, 0, 0, Gdx.graphics.getWidth(),
				Gdx.graphics.getHeight());
		batch.end();

		stage.act(Gdx.graphics.getDeltaTime());
		stage.draw();
		picturePanel =  new PicturePanel();
	}
	@Override
	public void dispose() {
		backGroudTexture.dispose();
		batch.dispose();
		skin.dispose();
		stage.dispose();
	}

	@Override
	public void hide() {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resize(int arg0, int arg1) {
	}

	@Override
	public void resume() {
	}
	
	@Override
	public void show() { 
		setLevel();
		createGameScreen();
		gm.checkButtons();
		//Add input processor for stage
		inputMultiplexer.addProcessor(stage);
		Gdx.input.setInputProcessor(inputMultiplexer);
	}

	/**
	 * This method will set the "Level" of the game.
	 * Also the duration of each word is set for the different levels. 
	 * */
	private void setLevel(){		
		String level = game.getterSetter.getLevel();
		if(level.equalsIgnoreCase("Level 1 - 4 letters") 
				|| level.equalsIgnoreCase("Default")){
			game.getterSetter.setLevel("LEVEL 1");
			backGroudTexture = new Texture(Gdx.files.internal("level1Screen.png"));
			hm = game.picture.getLevel1();
			gameTime = 60;		// 60 secs for level 1
			
		}else if(level.equalsIgnoreCase("Level 2 - 5 letters")){
			game.getterSetter.setLevel("LEVEL 2");
			backGroudTexture = new Texture(Gdx.files.internal("level2Screen.png"));
			hm = game.picture.getLevel2();
			gameTime = 50; // 50 secs for level 2
			
		}else if(level.equalsIgnoreCase("Level 3 - 6 letters")){
			game.getterSetter.setLevel("LEVEL 3");
			backGroudTexture = new Texture(Gdx.files.internal("level3Screen.png"));
			hm = game.picture.getLevel3();
			gameTime = 40; // 40 secs for level 3  , 5 sec for testing purposes
		}
	}// end of setLevel()
	
	
	/**
	 * Create the GameScreen, there are 4 main panels to the screen. 
	 * buttonPanel which contain all the textfields and textbuttons.
	 * rightPanel which contain the scorelabel and timer. 
	 * leftPanel which contain the Shortcut keys help.
	 * And the rootTable which contain the levelLabel and back button. 
	 * 
	 * */
	private void createGameScreen(){
		
		// Settting the game context
		setGameContext();

		//Initialise the Panels
		buttonPanel =  new ButtonPanel();
		rightPanel = new RightPanel();
		leftPanel = new LeftPanel();
		
		Table rootTable =  new Table();
		rootTable.setFillParent(true);
		
		//BitmapFont
		BitmapFont font = new BitmapFont(Gdx.files.internal("whitefont.fnt"), false);
		// Label Style for the labels
		labelStyle = new LabelStyle();
		labelStyle.font = font;
				
		// LEVEL LABEL
		levelLabel = new Label(game.getterSetter.getLevel(), labelStyle);

		// BACK BUTTON
		backButton =  new TextButton("Back", skin);
		backButton.addListener(new ChangeListener(){
			@Override
			public void changed(ChangeEvent arg0, Actor arg1) {
				clearEverything();
				game.setScreen(game.mainScreen);
			}});
		
		rootTable.add(backButton).expand(-5, 0).width(100f).height(40f).left();
		rootTable.add(levelLabel).expand(100, 0);
		rightPanel.setPosition(500, 80);
		leftPanel.setPosition(-550, 00);
		stage.addActor(rootTable.top());
		stage.addActor(rightPanel);
		stage.addActor(leftPanel);
		stage.addActor(buttonPanel.bottom()); 
		
		
	}
	
	/**
	 * This method will set the game context namely the word and texture. 
	 * */
	public void setGameContext(){
		//Reset the hint state
		game.getterSetter.setHint(false);
		
		Object[] cat =  hm.keySet().toArray();
		
		//Random select a category, if category is used before 
		//then will generate one more random category
		int num = new Random().nextInt(hm.size() - 1); 
		if(!catList.isEmpty()){
			if( catList.contains(cat[num])  )
				num = new Random().nextInt(hm.size() - 1); 
		}

		//Retrieving of category and texture.
		String category = "" + hm.keySet().toArray()[num];
		catList.add(category);
		
		String categoryItem = "" + hm.get(category).keySet().toArray()[0];
		Texture texture = hm.get(category).get(categoryItem);
		
		game.getterSetter.setCategory(category);
		game.getterSetter.setCategoryItem(categoryItem);
		game.getterSetter.setTexture(texture);

		// Calling the generateWord method to shuffler the character
		gm.generateWord(categoryItem);
		// change the text on the buttons.
		gm.checkButtons();
		// Reset the timer.
		gm.timer(gameTime);
		
		System.out.println(category + "  " + game.getterSetter.getCategoryItem() );
	}
	
	/**
	 * Check the letter agains the button letters. Removed the button letters
	 * as per key typed.
	 *  
	 * @param input - letter to be set
	 * */
	private void checkLetter(String letter){
		getInputText(letter);
		for(TextButton btn : buttonList){	
			if(letter.equalsIgnoreCase(btn.getText().toString())){
				btn.setText("");
				break;
			}
		}
	}
	
	/**
	 * Set the textfields based on the text from user. 
	 * @param input - letter to be set
	 * */
	public void getInputText(String input){
		input = input.toUpperCase();
		if(textfield0.getMessageText().isEmpty())		
			textfield0.setMessageText(input);
		else if(textfield1.getMessageText().isEmpty())
			textfield1.setMessageText(input);
		else if(textfield2.getMessageText().isEmpty())
			textfield2.setMessageText(input);
		else if(textfield3.getMessageText().isEmpty())
			textfield3.setMessageText(input);
		else if(textfield4.getMessageText().isEmpty())
			textfield4.setMessageText(input);
		else if(textfield5.getMessageText().isEmpty())
			textfield5.setMessageText(input);
		else if(textfield6.getMessageText().isEmpty())
			textfield6.setMessageText(input);
	}
	private void clearEverything(){
		levelLabel.clearActions();
		scoreLabel.clearActions();
	}
	
	public void clearInputText(){
		textfield0.setMessageText("");
		textfield1.setMessageText("");
		textfield2.setMessageText("");
		textfield3.setMessageText("");
		textfield4.setMessageText("");
		textfield5.setMessageText("");
		textfield6.setMessageText("");
	}

	//----------RIGHT PANEL-------------//
	//Right Panel to add score and time
	/**
	 * Inner Class for setting up of the RightPanel
	 * */
	private class RightPanel extends Table{
		
		RightPanel(){
			this.setFillParent(true);
			
			categoryLabel = new Label("Category : " + game.getterSetter.getCategory(), skin);
			this.add(categoryLabel).size(100, 50).padBottom(30).width(100).row();
			
			scoreLabel =  new Label("Score : " + 0 , skin);
			this.add(scoreLabel).size(100, 50).padBottom(30).width(100).row();
			
			
//			timeLabel = new Label("Time: " , skin);
//			this.add(timeLabel).padBottom(30).width(100).row();
		}
	}
	
	//----------BUTTON PANEL-------------//
	/**
	 * Inner Class for setting up of the ButtonPanel
	 * */
	private class ButtonPanel extends Table{
		
		ButtonPanel(){
			this.setFillParent(true);
			
			//Declarations of textfield
			textfield0 =  new TextField("",skin); 
			textfield0.setMessageText("");
			
			textfield1 =  new TextField("",skin); 
			textfield2 =  new TextField("",skin); 
			textfield3 =  new TextField("",skin); 
			textfield4 =  new TextField("",skin); 
			textfield5 =  new TextField("",skin); 
			textfield6 =  new TextField("",skin); 
			
			textfield1.setMessageText("");
			textfield2.setMessageText("");
			textfield3.setMessageText("");
			textfield4.setMessageText("");
			textfield5.setMessageText("");
			textfield6.setMessageText("");
			
			textfield1.setDisabled(true);
			textfield2.setDisabled(true);
			textfield3.setDisabled(true);
			textfield4.setDisabled(true);
			textfield5.setDisabled(true);
			textfield6.setDisabled(true);

			//Default level - Level 1 with 4 textfields.
			this.add(textfield1).width(50).height(50).spaceBottom(30);
			this.add(textfield2).width(50).height(50).spaceBottom(30);
			this.add(textfield3).width(50).height(50).spaceBottom(30);
			this.add(textfield4).width(50).height(50).spaceBottom(30);
			if(game.getterSetter.getLevel().equalsIgnoreCase("Level 2")){
				this.add(textfield5).width(50).height(50).spaceBottom(30);
			}else if(game.getterSetter.getLevel().equalsIgnoreCase("Level 3")){
				this.add(textfield5).width(50).height(50).spaceBottom(30);
				this.add(textfield6).width(50).height(50).spaceBottom(30);
			}
			this.row();
//			buttonList = new ArrayList<TextButton>();
			button1 =  new TextButton("" , skin);
			button2 =  new TextButton("" , skin);	
			button3 =  new TextButton("" , skin);
			button4 =  new TextButton("" , skin);
			button5 =  new TextButton("" , skin);
			button6 =  new TextButton("" , skin);
			button7 =  new TextButton("" , skin);
			button8 =  new TextButton("" , skin);
			button9 =  new TextButton("" , skin);
			button10 =  new TextButton("" , skin);
			
			buttonList.add(button1);
			buttonList.add(button2);
			buttonList.add(button3);
			buttonList.add(button4);
			buttonList.add(button5);
			buttonList.add(button6);
			buttonList.add(button7);
			buttonList.add(button8);
			buttonList.add(button9);
			buttonList.add(button10);
			
			buttonListeners();
			
			this.add(button1).width(70).height(50);
			this.add(button2).width(70).height(50);
			this.add(button3).width(70).height(50);
			this.add(button4).width(70).height(50);
			this.add(button5).width(70).height(50);
			this.row();
			this.add(button6).width(70).height(50);
			this.add(button7).width(70).height(50);
			this.add(button8).width(70).height(50);
			this.add(button9).width(70).height(50);
			this.add(button10).width(70).height(50);
		}
	}
	
	/**
	 * Method where all the buttons listeners are created. 
	 * */
	private void buttonListeners(){
		for(final TextButton btn : buttonList){
			btn.addListener(new ChangeListener(){
			@Override
			public void changed(ChangeEvent arg0, Actor arg1) {
				getButtonText(btn);
				btn.setText("");
			}});
		}
	}
	
	/**
	 * This method will called the getText and setText method to pass the 
	 * value from the button to the textfield 
	 * 
	 * @param button -  the button that is clicked
	 * */
	private void getButtonText(TextButton button){
		if(textfield1.getMessageText().isEmpty())
			textfield1.setMessageText("" + button.getText());
		else if(textfield2.getMessageText().isEmpty())
			textfield2.setMessageText("" + button.getText());
		else if(textfield3.getMessageText().isEmpty())
			textfield3.setMessageText("" + button.getText());
		else if(textfield4.getMessageText().isEmpty())
			textfield4.setMessageText("" + button.getText());
		else if(textfield5.getMessageText().isEmpty())
			textfield5.setMessageText("" + button.getText());
		else if(textfield6.getMessageText().isEmpty())
			textfield6.setMessageText("" + button.getText());
	}

	//----------PICTURE PANEL-------------//
	/**
	 * PicturePanel class is used for drawing the 
	 * new picture (texture on the screen)
	 * 
	 * */
	private class PicturePanel{
		PicturePanel(){
			Texture texture = game.getterSetter.getTexture(); 
			batch.begin(); 
			batch.draw(texture, 350, 200, 600,400);
	        batch.end();
	        getWindow().setVisible(false);
		}
	}
	//----------LEFT PANEL-------------//
	/**
	 * Left Panel class is use to create the labels for the game. 
	 * */
	private class LeftPanel extends Table{
		
		LeftPanel(){
			this.setFillParent(true);
			
			Label EscLabel =  new Label("ESC - Back to Arcade",skin); 

			Label EnterLabel =  new Label("ENTER - Check Answer",skin); 
			
			Label F1Label =  new Label("F1 - Hint",skin); 
			
			Label F2Label =  new Label("F2 - Select Category",skin); 
			
			this.add(EscLabel).size(100, 50).padBottom(30).row();
			this.add(EnterLabel).size(100, 50).padBottom(30).row();
			this.add(F1Label).size(100, 50).padBottom(30).row();
			this.add(F2Label).size(100, 50).padBottom(30).row();
		}
	}
	
	// Category Window
	/**
	 * This method will create a window for the categories. 
	 * 
	 * @return Category Window
	 * */
	public Window getWindow(){	
		Object[] categories = hm.keySet().toArray();
		final List categoryList =  new List(categories , skin);
		scrollpane = new ScrollPane(categoryList, skin);
		
		final Window categoryWindow = new Window("Category" , skin);
		categoryWindow.setWidth(250);
		categoryWindow.setHeight(150);
		categoryWindow.setPosition(30, 50);
		
		playButton =  new TextButton("Play" , skin);
		playButton.addListener(new ChangeListener(){
			@Override
			public void changed(ChangeEvent arg0, Actor arg1) {
				
				// Clear all the task in the timer
				gm.timer(0);				
//				HashMap<String, HashMap<String, Texture>> hm = null ;
//				if(level.equalsIgnoreCase("Level 1")){
//					hm = game.picture.getLevel1();
//				}else if(level.equalsIgnoreCase("Level 2")){
//					hm = game.picture.getLevel2();
//				}else if(level.equalsIgnoreCase("Level 3")){
//					hm = game.picture.getLevel3();
//				}
				String category = categoryList.getSelection();
				System.out.println(hm);
				String word =  "" + hm.get(category).keySet().toArray()[0];
				Texture texture = hm.get(category).get(word);
				game.getterSetter.setCategory(category);
				game.getterSetter.setTexture(texture);
				game.getterSetter.setCategoryItem(word);
				
				game.getterSetter.setAnswerCount(0);
				catList.add(category);	
				System.out.println("Category : " +  category + " Word :" + word );
				clearInputText();
				gm.generateWord(word);
				gm.checkButtons();
				categoryLabel.setText("Category : " +  category );
				gm.timer(gameTime);
				
				getWindow().setVisible(false);
			}});
		
		categoryWindow.add(scrollpane).width(categoryWindow.getWidth()).row();
		categoryWindow.add(playButton).width(100);
		
		stage.addActor(categoryWindow);
		
		return categoryWindow;
	}

	// INPUT PROCESSOR// 

	@Override
	public boolean keyDown(int code) {
		// TODO Auto-generated method stub

		if(code == Keys.BACKSPACE){
			gm.backSpaceFunction();
			gm.checkButtons();
		}
		else if(code == Keys.ENTER){
			gm.checkAnswer();
		}
		else if(code == Keys.F1){
			if(game.getterSetter.getHint() == false){
				gm.getHints();
				game.getterSetter.setHint(true);
			}
		}else if(code == Keys.F2){
			getWindow().setVisible(true);
		}
		
		return false;
	}

	@Override
	public boolean keyTyped(char c) {
		String s = c + ""; 
		for(String str : lettersCode){
			if(str.equalsIgnoreCase(s)){
				checkLetter(s);
			}
		}
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
	public boolean touchDown(int arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub
		return false;
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
}// end of GameScreen