package deco2800.arcade.guesstheword.GUI;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
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
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;

import deco2800.arcade.client.GameClient;
import deco2800.arcade.guesstheword.gameplay.Achievements;
import deco2800.arcade.guesstheword.gameplay.WordShuffler;
import deco2800.arcade.model.Achievement;
import deco2800.arcade.model.Game;

public class GameScreen implements Screen {

	private GuessTheWord game;
	private Skin skin;
	private SpriteBatch batch;
	private Stage stage;
	private ButtonPanel buttonPanel;
	private PicturePanel picturePanel;
	private RightPanel rightPanel;
	private LeftPanel leftPanel;
	private WordShuffler word;

	// The letters buttons for the games
	private TextButton button1, button2, button3, button4, button5, 
	button6	, button7, button8, button9, button10 , backButton, playButton;
	
	// The letters textfield for the game
	private TextField textfield1, textfield2, textfield3, 
					  textfield4, textfield5, textfield6;  
	
	private Label scoreLabel, levelLabel;
	private LabelStyle labelStyle;
	
	private ScrollPane scrollpane;
	
	private final BitmapFont font;
	
	private HashMap<String, HashMap<String, Texture>> hm;
	private ArrayList<TextButton> buttonList;
	private ArrayList<Integer> lettersKeyed;
	private ArrayList<String> catList;
	
    private String[] breakword;
    
    private Boolean hintTaken = false ;
    
    private int score = 0;     
    private int  count = 0;
    private int  hintPosition ;
    
    private float gametime = 0;    
    
//    private long startTime = System.currentTimeMillis();
	Texture texture;
	
	String level;

	GameScreen(final GuessTheWord game){
		this.game = game;
		this.skin = game.skin;
		batch =  new SpriteBatch();
		stage =  new Stage();
		font = new BitmapFont(Gdx.files.internal("blackfont.fnt"), false);

		//Creating Instances of the wordshuffler class 
		word =  new WordShuffler();
		
		hm = new HashMap<String, HashMap<String, Texture>>();
		lettersKeyed = new ArrayList<Integer>();
		catList = new ArrayList<String>();
	}

	/**
	 * This method will set the "Level" of the game.
	 * Also the duration of each word is set for the different levels. 
	 * */
	private void setLevel(){		
		String level = game.getterSetter.getLevel();
		catList.add(game.getterSetter.getCategory());
		
		if(level.equalsIgnoreCase("Level 1 - 4 letters") 
				|| level.equalsIgnoreCase("Default")){
			this.level = "LEVEL 1";	
			gametime = 62; // 60 secs for level 1
			hm = game.picture.getLevel1();
			setGameContext();
			
			
		}else if(level.equalsIgnoreCase("Level 2 - 5 letters")){
			this.level = "LEVEL 2";
			hm = game.picture.getLevel2();
			setGameContext();
			gametime = 52; // 50 secs for level 2
			
		}else if(level.equalsIgnoreCase("Level 3 - 6 letters")){
			this.level = "LEVEL 3";
			hm = game.picture.getLevel3();
			setGameContext();
			gametime = 42; // 40 secs for level 3  , 5 sec for testing purposes
		}
	}// end of setLevel()
	
	/**
	 * This method will set the  timer for the game.
	 * When the duration is up, the next word will be shown, 
	 * irregardless of correct or wrong answer. No points will be awarded.
	 * 
	 * */
	public void timer(){
		System.out.println("Timer started!!!");
		Timer.schedule(new Task(){
		    @Override
		    public void run() {
		    	System.out.println("Timer UP!!!");
		    	nextWord(game.getterSetter.getCategoryItem(),
		    			game.getterSetter.getCategory());
		    }
		}, gametime);
	}
	
	private void setGameContext(){
		
		Object[] cat =  hm.keySet().toArray();
		
		//Random select a category!
		int num = new Random().nextInt(hm.size() - 1); 
		if(catList.contains(cat[num])){
			num = new Random().nextInt(hm.size() - 1); 
		}

		//Retrieving of category and texture.
		String category = "" + hm.keySet().toArray()[num];
		String categoryItem = "" + hm.get(category).keySet().toArray()[0];
		Texture texture = hm.get(category).get(categoryItem);
		
		game.getterSetter.setCategory(category);
		game.getterSetter.setCategoryItem(categoryItem);
		game.getterSetter.setTexture(texture);

		checkButtons();

		System.out.println(category);
	}
	
	/**
	 * Create the GameScreen, there are 4 main panels to the screen. 
	 * buttonPanel which contain all the textfields and textbuttons.
	 * rightPanel which contain the scorelabel and timer. 
	 * leftPanel which contain the Shortcut keys help.
	 * And the rootTable which contain the levelLabel and back button. 
	 * 
	 * */
	private void createGameScreen(){
		
		buttonPanel =  new ButtonPanel();
		rightPanel = new RightPanel();
		leftPanel = new LeftPanel();
		
		Table rootTable =  new Table();
		rootTable.setFillParent(true);

		labelStyle = new LabelStyle();
		labelStyle.font = font;
		
		levelLabel = new Label(level, labelStyle);
		
		backButton =  new TextButton("Back", skin);
		
		backButton.addListener(new ChangeListener(){
			@Override
			public void changed(ChangeEvent arg0, Actor arg1) {

				game.setScreen(game.mainScreen);
			}});
		
		rootTable.add(backButton).expand(-5, 0).width(100f).height(40f).left();
		rootTable.add(levelLabel).expand(100, 0);
		
		stage.addActor(rootTable.top());
		stage.addActor(rightPanel.right());
		stage.addActor(leftPanel.left());
		stage.addActor(buttonPanel.bottom()); 
		
	}
	
	
	private void checkKeyboardInputs(){
		// Checking of A-Z inputs. 
		if(Gdx.input.isKeyPressed(Input.Keys.A)){
			CheckLetter(Input.Keys.A , "A");			
		}else if(Gdx.input.isKeyPressed(Input.Keys.B)){
			CheckLetter(Input.Keys.B , "B");
		}else if(Gdx.input.isKeyPressed(Input.Keys.C)){
			CheckLetter(Input.Keys.C , "C");
		}else if(Gdx.input.isKeyPressed(Input.Keys.D)){
			CheckLetter(Input.Keys.D , "D");
		}else if(Gdx.input.isKeyPressed(Input.Keys.E)){
			CheckLetter(Input.Keys.E , "E");
		}else if(Gdx.input.isKeyPressed(Input.Keys.F)){
			CheckLetter(Input.Keys.F , "F");
		}else if(Gdx.input.isKeyPressed(Input.Keys.G)){
			CheckLetter(Input.Keys.G , "G");
		}else if(Gdx.input.isKeyPressed(Input.Keys.H)){
			CheckLetter(Input.Keys.H , "H");
		}else if(Gdx.input.isKeyPressed(Input.Keys.I)){
			CheckLetter(Input.Keys.I , "I");
		}else if(Gdx.input.isKeyPressed(Input.Keys.J)){
			CheckLetter(Input.Keys.J , "J");
		}else if(Gdx.input.isKeyPressed(Input.Keys.K)){
			CheckLetter(Input.Keys.K , "K");
		}else if(Gdx.input.isKeyPressed(Input.Keys.L)){
			CheckLetter(Input.Keys.L , "L");
		}else if(Gdx.input.isKeyPressed(Input.Keys.M)){
			CheckLetter(Input.Keys.M , "M");
		}else if(Gdx.input.isKeyPressed(Input.Keys.N)){
			CheckLetter(Input.Keys.N , "N");
		}else if(Gdx.input.isKeyPressed(Input.Keys.O)){
			CheckLetter(Input.Keys.O , "O");
		}else if(Gdx.input.isKeyPressed(Input.Keys.P)){
			CheckLetter(Input.Keys.P , "P");
		}else if(Gdx.input.isKeyPressed(Input.Keys.Q)){
			CheckLetter(Input.Keys.Q , "Q");
		}else if(Gdx.input.isKeyPressed(Input.Keys.R)){
			CheckLetter(Input.Keys.R , "R");
		}else if(Gdx.input.isKeyPressed(Input.Keys.S)){
			CheckLetter(Input.Keys.S , "S");
		}else if(Gdx.input.isKeyPressed(Input.Keys.T)){
			CheckLetter(Input.Keys.T , "T");
		}else if(Gdx.input.isKeyPressed(Input.Keys.U)){
			CheckLetter(Input.Keys.U , "U");
		}else if(Gdx.input.isKeyPressed(Input.Keys.V)){
			CheckLetter(Input.Keys.V , "V");
		}else if(Gdx.input.isKeyPressed(Input.Keys.W)){
			CheckLetter(Input.Keys.W , "W");
		}else if(Gdx.input.isKeyPressed(Input.Keys.X)){
			CheckLetter(Input.Keys.X , "X");
		}else if(Gdx.input.isKeyPressed(Input.Keys.Y)){
			CheckLetter(Input.Keys.Y , "Y");
		}else if(Gdx.input.isKeyPressed(Input.Keys.Z)){
			CheckLetter(Input.Keys.Z , "Z");
		}
		
		// BACKSPACE - to clear all the textfields.
		if(Gdx.input.isKeyPressed(Input.Keys.BACKSPACE)){ 
			// If user used hint then the hint will not be cleared. 
			if(hintTaken == true){
				String let = "";
				if(hintPosition == 1){
					game.getterSetter.setText2("");
					game.getterSetter.setText3("");
					game.getterSetter.setText4("");
					game.getterSetter.setText5("");
					game.getterSetter.setText6("");
					
					let = game.getterSetter.getText1();
				}
				if(hintPosition == 2){
					game.getterSetter.setText1("");
					game.getterSetter.setText3("");
					game.getterSetter.setText4("");
					game.getterSetter.setText5("");
					game.getterSetter.setText6("");
					
					let = game.getterSetter.getText2();
				}
				if(hintPosition == 3){
					game.getterSetter.setText1("");
					game.getterSetter.setText2("");
					game.getterSetter.setText4("");
					game.getterSetter.setText5("");
					game.getterSetter.setText6("");
					
					let = game.getterSetter.getText3();
				}
				if(hintPosition == 4){
					game.getterSetter.setText1("");
					game.getterSetter.setText2("");
					game.getterSetter.setText3("");
					game.getterSetter.setText5("");
					game.getterSetter.setText6("");
					
					let = game.getterSetter.getText4();
				}
				if(hintPosition == 5){
					game.getterSetter.setText1("");
					game.getterSetter.setText2("");
					game.getterSetter.setText3("");
					game.getterSetter.setText4("");
					game.getterSetter.setText6("");
					
					let = game.getterSetter.getText5();
				}
				if(hintPosition == 6){
					game.getterSetter.setText1("");
					game.getterSetter.setText2("");
					game.getterSetter.setText3("");
					game.getterSetter.setText4("");
					game.getterSetter.setText5("");
					
					let = game.getterSetter.getText6();
				}
				
				for(int i = 0; i < buttonList.size(); i++){	
					if(let.equalsIgnoreCase(
							buttonList.get(i).getText().toString())){
						buttonList.get(i).setText("");
					}else 
						buttonList.get(i).setText(breakword[i]);
				}
				
			}else {
				clearInputText();
				for(int i = 0; i < buttonList.size(); i++){
				buttonList.get(i).setText(breakword[i]);
				}	
				
			}
		} 
		
		// back to main menu
		if(Gdx.input.isKeyPressed(Input.Keys.ESCAPE)){
			System.out.println("Back to MainScreen");
			clearInputText();
			// Clear all the task in the timer
//			Timer.instance.clear();
			game.setScreen(game.mainScreen);
		} 
		
		// ENTER - check answers
		if(Gdx.input.isKeyPressed(Input.Keys.ENTER)){
			System.out.println("Check answers");
			checkAnswer();
		} 
		
		// F1 - HINT
		if(Gdx.input.isKeyPressed(Input.Keys.F1)){
			System.out.println("Hint used, Points - 5");
			
			// User can only use hint for once!
			if(hintTaken == false){
				hintPosition = getHints();
				hintTaken = true;
			}
		}
		
		// F2 - CATEGORY
		if(Gdx.input.isKeyPressed(Input.Keys.F2)){
			System.out.println("Change Category"); 
			
			// Stop the timer
//			Timer.instance.stop();
			
			getWindow().setVisible(true);
		}
		
		
	}// end of check keyboards method 
	
	// this method will check the button clicked and 
	// empty the button text. 
	private void CheckLetter(int key, String letter){
		if(lettersKeyed.contains(key)){
			lettersKeyed.clear();
			getInputText(letter);
			for(TextButton btn : buttonList){	
				if(letter.equalsIgnoreCase(btn.getText().toString())){
					btn.setText("");
				}
			}
		}else{
			lettersKeyed.add(key);
		}
	}
	
	//  PROVIDE HINTS
	private int getHints(){
		// For each hint, 5 points will be deducted
		score -= 5;
		String actualWord = game.getterSetter.getCategoryItem();
		String hint = word.getHint(actualWord.toCharArray());
		
		for(TextButton btn : buttonList){	
			if(hint.equalsIgnoreCase(btn.getText().toString())){
				btn.setText("");
			}
		}
		int position = actualWord.indexOf(hint) + 1;
		if(position == 1){
			game.getterSetter.setText1(hint);
		}
		if(position == 2){
			game.getterSetter.setText2(hint);
		}	
		if(position == 3){
			game.getterSetter.setText3(hint);
		}
		if(position == 4){
			game.getterSetter.setText4(hint);
		}
		if(position == 5){
			game.getterSetter.setText5(hint);
		}
		if(position == 6){
			game.getterSetter.setText6(hint);
		}
		System.out.println("Hint provided, " +  hint + " @ positon " + position);
		return position;
		
	}
	// CHECK ANSWER
	private void checkAnswer(){
		StringBuilder userAnswer = new StringBuilder();
		userAnswer.append(textfield1.getText());
		userAnswer.append(textfield2.getText());
		userAnswer.append(textfield3.getText());
		userAnswer.append(textfield4.getText()); 
		
		if(level.equalsIgnoreCase("Level 2")){
			userAnswer.append(textfield5.getText());
		}
		if(level.equalsIgnoreCase("Level 3")){
			userAnswer.append(textfield5.getText());
			userAnswer.append(textfield6.getText());
		}
		String answer = game.getterSetter.getCategoryItem();
		if(userAnswer.toString().equalsIgnoreCase(answer)){
			count ++;
			String category = game.getterSetter.getCategory();
//			String word = game.getterSetter.getCategoryItem();
			
			if(count > 5){
				clearInputText();
				setGameContext();
				
				count = 0; 
			}else {
				score += 10;
				scoreLabel.setText("Score: " + score);
				game.incrementAchievement("guesstheword.animals");
				nextWord(answer, category);

			}
		}else{
			clearInputText();
			for(int i = 0; i < buttonList.size(); i++){
				//System.out.print(breakword[i]+ " ");
				buttonList.get(i).setText(breakword[i]);
			}
		}
//		System.out.println(userAnswer.toString());	
	}
	
	// Search for the next word. used by check answers and timer. 
	private void nextWord(String answer ,  String category){
		// Changing of word, then the hint is reset
		hintTaken = false;
		Texture newTexture = null;
		Object[] next = null;

		next = hm.get(category).keySet().toArray();
		
		for(int i = 0; i < next.length-1; i ++){
			if(next[i].equals(answer)){
				
				newTexture = hm.get(category).get(next[i+1]);
				answer = next[i+1].toString();
				game.getterSetter.setCategoryItem(answer);
				game.getterSetter.setTexture(newTexture);
				clearInputText();
				checkButtons();
				// Clear all the task in the timer
				// and start the timer again
//				timer.clear();
//				timer.start();    // reset the timer. 
				
				break;
			}// end of nested if
		}// end of  for loop
	}
	
	
	// SET TEXTFIELD BASED ON INPUT.
	private void getInputText(String input){
		if(game.getterSetter.getText1().isEmpty())
			game.getterSetter.setText1(input);
		else if(game.getterSetter.getText2().isEmpty())
			game.getterSetter.setText2(input);
		else if(game.getterSetter.getText3().isEmpty())
			game.getterSetter.setText3(input);
		else if(game.getterSetter.getText4().isEmpty())
			game.getterSetter.setText4(input);
		else if(game.getterSetter.getText5().isEmpty())
			game.getterSetter.setText5(input);
		else if(game.getterSetter.getText6().isEmpty())
			game.getterSetter.setText6(input);
	}
	
	private void clearInputText(){
		game.getterSetter.setText1("");
		game.getterSetter.setText2("");
		game.getterSetter.setText3("");
		game.getterSetter.setText4("");
		game.getterSetter.setText5("");
		game.getterSetter.setText6("");
	}

	//----------RIGHT PANEL-------------//
	//Right Panel to add score and time
	private class RightPanel extends Table{
		
		RightPanel(){
			this.setFillParent(true);
			scoreLabel =  new Label("Score: 0" , skin);
			this.add(scoreLabel).padBottom(30).width(100).row();
			
//			timeLabel = new Label("Time: " , skin);
//			this.add(timeLabel).padBottom(30).width(100).row();
		}
	}
	
	//----------BUTTON PANEL-------------//
	private class ButtonPanel extends Table{
		
		ButtonPanel(){
			this.setFillParent(true);
			
			//Declarations of textfield
			textfield1 =  new TextField("",skin); 
			textfield2 =  new TextField("",skin); 
			textfield3 =  new TextField("",skin); 
			textfield4 =  new TextField("",skin); 
			textfield5 =  new TextField("",skin); 
			textfield6 =  new TextField("",skin); 
			
			//Default level - Level 1 with 4 textfields.
			this.add(textfield1).width(50).height(50).spaceBottom(30);
			this.add(textfield2).width(50).height(50).spaceBottom(30);
			this.add(textfield3).width(50).height(50).spaceBottom(30);
			this.add(textfield4).width(50).height(50).spaceBottom(30);
			if(level.equalsIgnoreCase("Level 2")){
				this.add(textfield5).width(50).height(50).spaceBottom(30);
			}else if(level.equalsIgnoreCase("Level 3")){
				this.add(textfield5).width(50).height(50).spaceBottom(30);
				this.add(textfield6).width(50).height(50).spaceBottom(30);
			}
			this.row();
			
			buttonList = new ArrayList<TextButton>();
			
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
			
			buttonListeners();
		}
	}
	
	private void checkTextfield(){	
		textfield1.setText(game.getterSetter.getText1()); 
		textfield2.setText(game.getterSetter.getText2()); 
		textfield3.setText(game.getterSetter.getText3());
		textfield4.setText(game.getterSetter.getText4()); 
		textfield5.setText(game.getterSetter.getText5());
		textfield6.setText(game.getterSetter.getText6()); 
	}// end of checkTextfield
	
	// Set the buttons text to the letters from the word.
	private void checkButtons(){
		String category =  game.getterSetter.getCategoryItem();
		
		breakword = word.breakWord(category);
			
		for(int i = 0; i < buttonList.size(); i++){
			buttonList.get(i).setText(breakword[i]);
		}
	}
	
	private void buttonListeners(){
		button1.addListener(new ChangeListener(){
			@Override
			public void changed(ChangeEvent arg0, Actor arg1) {
				System.out.println("Button1 = " + button1.getText());
				getButtonText(button1);
				button1.setText("");
			}});
		button2.addListener(new ChangeListener(){
			@Override
			public void changed(ChangeEvent arg0, Actor arg1) {
				getButtonText(button2);
				button2.setText("");
			}});	
		button3.addListener(new ChangeListener(){
			@Override
			public void changed(ChangeEvent arg0, Actor arg1) {
				getButtonText(button3);
				button3.setText("");
			}});	
		button4.addListener(new ChangeListener(){
			@Override
			public void changed(ChangeEvent arg0, Actor arg1) {
				getButtonText(button4);
				button4.setText("");
			}});	
		button5.addListener(new ChangeListener(){
			@Override
			public void changed(ChangeEvent arg0, Actor arg1) {
				getButtonText(button5);
				button5.setText("");
			}});	
		button6.addListener(new ChangeListener(){
			@Override
			public void changed(ChangeEvent arg0, Actor arg1) {
				getButtonText(button6);
				button6.setText("");
			}});
		button7.addListener(new ChangeListener(){
			@Override
			public void changed(ChangeEvent arg0, Actor arg1) {
				getButtonText(button7);
				button7.setText("");
			}});
		button8.addListener(new ChangeListener(){
			@Override
			public void changed(ChangeEvent arg0, Actor arg1) {
				getButtonText(button8);
				button8.setText("");
			}});
		button9.addListener(new ChangeListener(){
			@Override
			public void changed(ChangeEvent arg0, Actor arg1) {
				getButtonText(button9);
				button9.setText("");
			}});
		button10.addListener(new ChangeListener(){
			@Override
			public void changed(ChangeEvent arg0, Actor arg1) {
				getButtonText(button10);
				button10.setText("");
			}});
	}
	
	private void getButtonText(TextButton button){
		if(game.getterSetter.getText1().isEmpty())
			game.getterSetter.setText1("" + button.getText());
		else if(game.getterSetter.getText2().isEmpty())
			game.getterSetter.setText2("" + button.getText());
		else if(game.getterSetter.getText3().isEmpty())
			game.getterSetter.setText3("" + button.getText());
		else if(game.getterSetter.getText4().isEmpty())
			game.getterSetter.setText4("" + button.getText());
		else if(game.getterSetter.getText5().isEmpty())
			game.getterSetter.setText5("" + button.getText());
		else if(game.getterSetter.getText6().isEmpty())
			game.getterSetter.setText6("" + button.getText());
	}

	//----------PICTURE PANEL-------------//
	private class PicturePanel{
		
		PicturePanel(){
			Texture texture = game.getterSetter.getTexture(); 
			batch.begin(); 
			batch.draw(texture, 350, 200, 600,400);
	        batch.end();
		}
	}
	//----------LEFT PANEL-------------//
	private class LeftPanel extends Table{
		
		LeftPanel(){
			this.setFillParent(true);
			
			Label EscLabel =  new Label("ESC - Back to Main Menu",skin); 

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
	private Window getWindow(){
		HashMap<String, HashMap<String, Texture>> hm = null ;
		if(level.equalsIgnoreCase("Level 1")){
			hm = game.picture.getLevel1();
		}else if(level.equalsIgnoreCase("Level 2")){
			hm = game.picture.getLevel2();
		}else if(level.equalsIgnoreCase("Level 3")){
			hm = game.picture.getLevel3();
		}
		
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
				
				HashMap<String, HashMap<String, Texture>> hm = null ;
				if(level.equalsIgnoreCase("Level 1")){
					hm = game.picture.getLevel1();
				}else if(level.equalsIgnoreCase("Level 2")){
					hm = game.picture.getLevel2();
				}else if(level.equalsIgnoreCase("Level 3")){
					hm = game.picture.getLevel3();
				}
				String category = categoryList.getSelection();
				String word =  "" + hm.get(category).keySet().toArray()[0];
				Texture texture = hm.get(category).get(word);
				game.getterSetter.setCategory(category);
				game.getterSetter.setTexture(texture);
				game.getterSetter.setCategoryItem(word);
				catList.add(category);	
				
				clearInputText();
				checkButtons();

				categoryWindow.setVisible(false);
			}});
		
		categoryWindow.add(scrollpane).width(categoryWindow.getWidth()).row();
		categoryWindow.add(playButton).width(100);
		
		stage.addActor(categoryWindow);
		
		return categoryWindow;
	}
	
	@Override
	public void render(float arg0) {
		Gdx.graphics.setContinuousRendering(false);
		Gdx.gl.glClearColor( 0f, 0f, 75f, 70f );
        Gdx.gl.glClear( GL20.GL_COLOR_BUFFER_BIT );
       
		stage.act(Gdx.graphics.getDeltaTime());
		stage.draw();
		checkKeyboardInputs();	
		checkTextfield();
		picturePanel =  new PicturePanel();
	}
	
	@Override
	public void show() { 
		setLevel();
		createGameScreen();
		checkButtons();
//		timer();
		
		Gdx.input.setInputProcessor(stage);
	}
	
	@Override
	public void dispose() {
		texture.dispose();
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


}// end of GameScreen