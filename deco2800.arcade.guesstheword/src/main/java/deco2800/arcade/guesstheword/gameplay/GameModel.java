package deco2800.arcade.guesstheword.gameplay;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;

import deco2800.arcade.guesstheword.GUI.GameScreen;
import deco2800.arcade.guesstheword.GUI.GuessTheWord;


/**
 * Game model for the Game Screen
 * 
 * @author Xu Duangui
 * */
public class GameModel {
	//--------------------------
	//PRIVATE VARIABLES
	//--------------------------
	/**
	 * GuessTheWord instance
	 * */
	private GuessTheWord game;
	/**
	 * GameScreen instance
	 * */
	private GameScreen gameScreen;
	/**
	 * WordShuffler instance
	 * */
	private WordShuffler word;
	
	/**
	 * To keep track of the score and used for updating score label.
	 * */
	private int score = 0;	
	/**
	 * To keep track of the number of correct ans
	 * */
	private int count = 0;	
	
	/**
	 * Array to store the generated words from word shuffler.
	 * */
	private String [] breakword;
	
	/**
	 * HashMap where the textures are stored.
	 * */
	private HashMap<String, HashMap<String, Texture>> hm;
	
	/**
	 * Constructor
	 * @param game -  instance of the game from GuessTheWord
	 * @param gameScreen -  instance of GameScreen
	 * @param word - instance of WordShuffler
	 * */
	public GameModel(GuessTheWord game, GameScreen gameScreen, WordShuffler word){
		this.game = game;
		this.gameScreen = gameScreen;
		this.word =  word;
	}
	
	//=============================================================
	//Overall Game Play: 
	// 1) Get Hint
	// 2) Check Answer
	// 3) Next Word
	//=============================================================
	/**
	 * Get the Hint from the word. 
	 * 
	 * @return the position of the hint in the word 
	 * */
	public int getHints(){
		game.getterSetter.setHint(true);
		
		//Called for Achievement
		game.incrementAchievement("guesstheword.hint");
		
		// For each hint, 5 points will be deducted
		game.getterSetter.setScore(-5);
		
		String actualWord = game.getterSetter.getCategoryItem();
		
		//Generate a random letter from WordShuffler
		String hint = word.getHint(actualWord.toCharArray());
		
		for(TextButton btn : gameScreen.buttonList){	
			if(hint.equalsIgnoreCase(btn.getText().toString())){
				btn.setText("");
			}
		}
		int position = actualWord.indexOf(hint) + 1;
		if(position == 1)
			gameScreen.textfield1.setMessageText(hint);
		if(position == 2)
			gameScreen.textfield2.setMessageText(hint);
		if(position == 3)
			gameScreen.textfield3.setMessageText(hint);
		if(position == 4)
			gameScreen.textfield4.setMessageText(hint);
		if(position == 5)
			gameScreen.textfield5.setMessageText(hint);
		if(position == 6)
			gameScreen.textfield6.setMessageText(hint);

		System.out.println("Hint provided, " +  hint + " @ positon " + position);
		return position;
		
	}
	
	/**
	 * Generate the next word after the user 
	 * has answered correctly or Timer is up.
	 * 
	 * @param answer -  the current word
	 * @param category -  the category that the word belong. 
	 * */
	public void nextWord(String answer ,  String category){
		hm =  gameScreen.hm;	
		Texture newTexture = null;
		Object[] next = hm.get(category).keySet().toArray();
		for(int i = 0; i < next.length-1; i ++){
			if(next[i].equals(answer)){
				newTexture = hm.get(category).get(next[i+1]);
				answer = next[i+1].toString();
				game.getterSetter.setCategoryItem(answer);
				game.getterSetter.setTexture(newTexture);
				
				generateWord(answer);
				gameScreen.clearInputText();
				checkButtons();
				break;
			}// end of nested if
		}// end of  for loop
	}
	
	/**
	 * This method will check the answer in which the user type. 
	 * */
	public void checkAnswer(){
		// using a String builder to append the user inputs 
		StringBuilder userAnswer = new StringBuilder();
		userAnswer.append(gameScreen.textfield1.getMessageText());
		userAnswer.append(gameScreen.textfield2.getMessageText());
		userAnswer.append(gameScreen.textfield3.getMessageText());
		userAnswer.append(gameScreen.textfield4.getMessageText()); 
		
		if(game.getterSetter.getLevel().equalsIgnoreCase("Level 2")){
			userAnswer.append(gameScreen.textfield5.getMessageText());
		}
		if(game.getterSetter.getLevel().equalsIgnoreCase("Level 3")){
			userAnswer.append(gameScreen.textfield5.getMessageText());
			userAnswer.append(gameScreen.textfield6.getMessageText());
		}
		
		String answer = game.getterSetter.getCategoryItem();
		
		if(userAnswer.toString().equalsIgnoreCase(answer)){
			count++;
//			game.getterSetter.setAnswerCount(count);
			
			String category = game.getterSetter.getCategory();
			
			// This means the user had finish the category. 
			if(count < 6){
				
				//Stop timer 
				timer(0);
				
				// Add 10 points to score.
				score += 10;
				
				// Update ScoreLabel and set score to setter method.
				gameScreen.scoreLabel.setText("Score : " + score);
				game.getterSetter.setScore(score);
				
				//When counter hit 5 it means the specific category is done.
				if(count == 5){
					// Clear game content and set up the next word content
					gameScreen.clearInputText();
					gameScreen.setGameContext();
					
					// Push the score to the high score counter.
					setHighScoreCounter();
					gameScreen.setCategoryLabel();
					//Reset Count
					resetCounter();
//					game.getterSetter.setAnswerCount(0);
				}
				else{
					//Achievement will be prompted.
					setAchievement(category);
					
					// Search for next word.
					nextWord(answer, category);
				}
			}
		}else{
			// Clear the textField and return the letters to the button
			gameScreen.clearInputText();
			checkButtons();
		}
	}
	
	/**
	 * Increment the Achievement based on the category. 
	 * 
	 * @param category - the category which user is playing.*/
	private void setAchievement(String category){
		if(category.equalsIgnoreCase("Animals"))
			game.incrementAchievement("guesstheword.animals");
		if(category.equalsIgnoreCase("Brands"))
			game.incrementAchievement("guesstheword.brands");
		if(category.equalsIgnoreCase("Countries"))
			game.incrementAchievement("guesstheword.countries");
		if(category.equalsIgnoreCase("Sports"))
			game.incrementAchievement("guesstheword.sports");
		if(category.equalsIgnoreCase("Transports"))
			game.incrementAchievement("guesstheword.transports");
	}
	
	/**
	 * Call the respective highScore client to update the score to database.
	 * */
	private void setHighScoreCounter(){
		String level = game.getterSetter.getLevel();
		if(level.equalsIgnoreCase("Level 1"))
			game.playerScore.countScore(game.playerScore.highScorePlayer1);
		else if(level.equalsIgnoreCase("Level 2"))
			game.playerScore.countScore(game.playerScore.highScorePlayer2);
		else if(level.equalsIgnoreCase("Level 3"))
			game.playerScore.countScore(game.playerScore.highScorePlayer3);
	}
	
	//--------------------------
	//PUBLIC METHODS
	//--------------------------
	
	/**
	 * Reset The Answer Counter
	 * */
	public void resetCounter(){
		count = 0;
	}
	
	/**
	 * Timer method -  timer will stop and clear if time = 0.
	 * 
	 *  @param time -  time for timer to start the task.
	 * */
	public void timer(int time){
		if(time == 0){
			Timer.instance.clear();
//			System.out.println("Timer stop!!!");
		}else{
//			System.out.println("Timer started!!!");
			Timer.instance.start();
			Timer.schedule(new Task(){
			    @Override
			    public void run() {
//			    	System.out.println("Timer UP!!!");
			    	nextWord(game.getterSetter.getCategoryItem(),
			    			game.getterSetter.getCategory());
			    }
			}, time , time , 5);
		}
	}// end of timer
	
	/**
	 * Back space functions. All the textfields will be clear if there is 
	 * a hint used then only the unknown letters will be deleted. 
	 * */
	public void backSpaceFunction(){
		int hintPosition = game.getterSetter.getHintPosition();
		// BACKSPACE - to clear all the textfields.
		// If user used hint then the hint will not be cleared. 
		if(game.getterSetter.getHint() == true){
			String let = "";
			if(hintPosition == 1){
				gameScreen.textfield2.setMessageText("");
				gameScreen.textfield3.setMessageText("");
				gameScreen.textfield4.setMessageText("");
				gameScreen.textfield5.setMessageText("");
				gameScreen.textfield6.setMessageText("");

				let = gameScreen.textfield1.getMessageText();
			}
			if(hintPosition == 2){
				gameScreen.textfield1.setMessageText("");
				gameScreen.textfield3.setMessageText("");
				gameScreen.textfield4.setMessageText("");
				gameScreen.textfield5.setMessageText("");
				gameScreen.textfield6.setMessageText("");

				let = gameScreen.textfield2.getMessageText();
			}
			if(hintPosition == 3){
				gameScreen.textfield1.setMessageText("");
				gameScreen.textfield2.setMessageText("");
				gameScreen.textfield4.setMessageText("");
				gameScreen.textfield5.setMessageText("");
				gameScreen.textfield6.setMessageText("");

				let = gameScreen.textfield3.getMessageText();
			}
			if(hintPosition == 4){
				gameScreen.textfield1.setMessageText("");
				gameScreen.textfield2.setMessageText("");
				gameScreen.textfield3.setMessageText("");
				gameScreen.textfield5.setMessageText("");
				gameScreen.textfield6.setMessageText("");

				let = gameScreen.textfield4.getMessageText();
			}
			if(hintPosition == 5){
				gameScreen.textfield1.setMessageText("");
				gameScreen.textfield2.setMessageText("");
				gameScreen.textfield3.setMessageText("");
				gameScreen.textfield4.setMessageText("");
				gameScreen.textfield6.setMessageText("");

				let = gameScreen.textfield5.getMessageText();
			}
			if(hintPosition == 6){
				gameScreen.textfield1.setMessageText("");
				gameScreen.textfield2.setMessageText("");
				gameScreen.textfield3.setMessageText("");
				gameScreen.textfield4.setMessageText("");
				gameScreen.textfield5.setMessageText("");

				let = gameScreen.textfield6.getMessageText();
			}
			
			for(int i = 0; i < gameScreen.buttonList.size(); i++){	
				if(let.equalsIgnoreCase(
						gameScreen.buttonList.get(i).getText().toString())){
					gameScreen.buttonList.get(i).setText("");
				}else 
					gameScreen.buttonList.get(i).setText(breakword[i]);
			}
			
		}else {
			gameScreen.clearInputText();
			for(int i = 0; i < gameScreen.buttonList.size(); i++){
				gameScreen.buttonList.get(i).setText(breakword[i]);
			}	
			
		}
	} // end of backSpaceFunctions
	
	/**
	 * Method to set the buttons text based on the word generated.
	 * */
	public void checkButtons(){
		for(int i = 0; i < gameScreen.buttonList.size(); i++){
			gameScreen.buttonList.get(i).setText(breakword[i]);
		}
	}//end of checkButtons
	
	// Calling the word generator to generate the word.
	public void generateWord(String category){
//		checkWordOccurance(category);
		breakword = word.breakWord(category);
	}
	
	/**
	 * Check for any repetition of letter in the word, eg.  dell
	 * @param str -  the word to be check on
	 * 
	 * @return true - if there is a repetition else false
	 * */
	public boolean checkWordOccurance(String str){
		 Map<Character, Integer> charMap = new HashMap<Character, Integer>();
		  char[] arr = str.toCharArray();
		  
		  	// Loop to check the same letter in the word.
		    for (char value: arr) {
		       if (Character.isAlphabetic(value)) {
		           if (charMap.containsKey(value)) {
		               charMap.put(value, charMap.get(value) + 1);

		           } else {
		               charMap.put(value, 1);
		           }
		       }
		    }
		    
		    // Return true if there is a value that is 2 or higher.
		    for(int i = 2; i < str.length(); i ++){
		    	 if(charMap.values().contains(i))
				    	return true;
		    }
		   return false;
	}
}
