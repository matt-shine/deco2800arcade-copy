package deco2800.arcade.guesstheword.gameplay;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;

import deco2800.arcade.guesstheword.GUI.GameScreen;
import deco2800.arcade.guesstheword.GUI.GuessTheWord;
import deco2800.arcade.guesstheword.GUI.MainScreen;

public class GameModel {
	
	private GuessTheWord game;
	private GameScreen gameScreen;
	private WordShuffler word;
	
	private int score = 0;
	private int count = 0;
	private int hintPosition;
	private boolean hintTaken;
	private String [] breakword;
	
	public HashMap<String, HashMap<String, Texture>> hm;
	
	
	public GameModel(GuessTheWord game, GameScreen gameScreen, WordShuffler word){
		this.game = game;
		this.gameScreen = gameScreen;
		this.word =  word;
	}
	
	
	public int getHints(){
		hintTaken = false;
		
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
	
	// Search for the next word. used by check answers and timer. 
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
		
//		System.out.println(userAnswer + " and " + answer);
		if(userAnswer.toString().equalsIgnoreCase(answer)){
			count ++;
			String category = game.getterSetter.getCategory();
			
			if(count > 5){
				gameScreen.clearInputText();
				gameScreen.setGameContext();
				
				setHighScoreCounter();
				
				count = 0; 
			}else {
				timer(0);
				score += 10;
				
				gameScreen.scoreLabel.setText("Score : " + score);
				game.getterSetter.setScore(score);
				
				setAchievement(category);
				
				nextWord(answer, category);
				hintTaken = false;
				
			}
		}else{
			// Clear the textField and return the letters to the button
			gameScreen.clearInputText();
			checkButtons();
		}
	}
	
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
	
	private void setHighScoreCounter(){
		String level = game.getterSetter.getLevel();
		if(level.equalsIgnoreCase("Level 1"))
			game.playerScore.countScore(game.playerScore.highScorePlayer1);
		else if(level.equalsIgnoreCase("Level 2"))
			game.playerScore.countScore(game.playerScore.highScorePlayer2);
		if(level.equalsIgnoreCase("Level 3"))
			game.playerScore.countScore(game.playerScore.highScorePlayer3);
	}
	
	//timer method
	public void timer(int time){
		if(time == 0){
			Timer.instance.stop();
			System.out.println("Timer stop!!!");
		}else{
			System.out.println("Timer started!!!");
			Timer.instance.start();
			Timer.schedule(new Task(){
			    @Override
			    public void run() {
			    	System.out.println("Timer UP!!!");
			    	nextWord(game.getterSetter.getCategoryItem(),
			    			game.getterSetter.getCategory());
			    }
			}, time , time , 5);
		}
	}
	
	
	public void checkKeyboardInputs(){
		// Checking of A-Z inputs. 
		if(Gdx.input.isKeyPressed(Input.Keys.A)){
			checkLetter(Input.Keys.A , "A");			
		}else if(Gdx.input.isKeyPressed(Input.Keys.B)){
			checkLetter(Input.Keys.B , "B");
		}else if(Gdx.input.isKeyPressed(Input.Keys.C)){
			checkLetter(Input.Keys.C , "C");
		}else if(Gdx.input.isKeyPressed(Input.Keys.D)){
			checkLetter(Input.Keys.D , "D");
		}else if(Gdx.input.isKeyPressed(Input.Keys.E)){
			checkLetter(Input.Keys.E , "E");
		}else if(Gdx.input.isKeyPressed(Input.Keys.F)){
			checkLetter(Input.Keys.F , "F");
		}else if(Gdx.input.isKeyPressed(Input.Keys.G)){
			checkLetter(Input.Keys.G , "G");
		}else if(Gdx.input.isKeyPressed(Input.Keys.H)){
			checkLetter(Input.Keys.H , "H");
		}else if(Gdx.input.isKeyPressed(Input.Keys.I)){
			checkLetter(Input.Keys.I , "I");
		}else if(Gdx.input.isKeyPressed(Input.Keys.J)){
			checkLetter(Input.Keys.J , "J");
		}else if(Gdx.input.isKeyPressed(Input.Keys.K)){
			checkLetter(Input.Keys.K , "K");
		}else if(Gdx.input.isKeyPressed(Input.Keys.L)){
			checkLetter(Input.Keys.L , "L");
		}else if(Gdx.input.isKeyPressed(Input.Keys.M)){
			checkLetter(Input.Keys.M , "M");
		}else if(Gdx.input.isKeyPressed(Input.Keys.N)){
			checkLetter(Input.Keys.N , "N");
		}else if(Gdx.input.isKeyPressed(Input.Keys.O)){
			checkLetter(Input.Keys.O , "O");
		}else if(Gdx.input.isKeyPressed(Input.Keys.P)){
			checkLetter(Input.Keys.P , "P");
		}else if(Gdx.input.isKeyPressed(Input.Keys.Q)){
			checkLetter(Input.Keys.Q , "Q");
		}else if(Gdx.input.isKeyPressed(Input.Keys.R)){
			checkLetter(Input.Keys.R , "R");
		}else if(Gdx.input.isKeyPressed(Input.Keys.S)){
			checkLetter(Input.Keys.S , "S");
		}else if(Gdx.input.isKeyPressed(Input.Keys.T)){
			checkLetter(Input.Keys.T , "T");
		}else if(Gdx.input.isKeyPressed(Input.Keys.U)){
			checkLetter(Input.Keys.U , "U");
		}else if(Gdx.input.isKeyPressed(Input.Keys.V)){
			checkLetter(Input.Keys.V , "V");
		}else if(Gdx.input.isKeyPressed(Input.Keys.W)){
			checkLetter(Input.Keys.W , "W");
		}else if(Gdx.input.isKeyPressed(Input.Keys.X)){
			checkLetter(Input.Keys.X , "X");
		}else if(Gdx.input.isKeyPressed(Input.Keys.Y)){
			checkLetter(Input.Keys.Y , "Y");
		}else if(Gdx.input.isKeyPressed(Input.Keys.Z)){
			checkLetter(Input.Keys.Z , "Z");
		}

		// BACKSPACE - to clear all the textfields.
		if(Gdx.input.isKeyPressed(Input.Keys.BACKSPACE)){ 
			// If user used hint then the hint will not be cleared. 
			if(hintTaken == true){
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
		} 
		
		// back to main menu
		if(Gdx.input.isKeyPressed(Input.Keys.ESCAPE)){
			System.out.println("Back to MainScreen");
			gameScreen.clearInputText();
			game.setScreen(new MainScreen(game));
		} 
		
		// ENTER - check answers
		if(Gdx.input.isKeyPressed(Input.Keys.ENTER)){
			System.out.println("Check answers");
			checkAnswer();
		} 
		
		// F1 - HINT
		if(Gdx.input.isKeyPressed(Input.Keys.F1)){
			
			// User can only use hint for once!
			if(hintTaken == false){
				hintPosition = getHints();
				hintTaken = true;
			}
		}
		
		// F2 - CATEGORY
		if(Gdx.input.isKeyPressed(Input.Keys.F2)){
			//Stop TImer
			timer(0);
			gameScreen.getWindow().setVisible(true);
			System.out.println("Change Category");
			
			// Stop the timer
//			Timer.instance.stop();
		}
	}// end of check keyboards method 
	
	// this method will check the button clicked and 
	// empty the button text. 
	public void checkLetter(int key, String letter){
		gameScreen.getInputText(letter);
		for(TextButton btn : gameScreen.buttonList){	
			if(letter.equalsIgnoreCase(btn.getText().toString())){
				btn.setText("");
				break;
			}
		}
	}
	
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
	
	// method to check for the number of occurance in the word 
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
