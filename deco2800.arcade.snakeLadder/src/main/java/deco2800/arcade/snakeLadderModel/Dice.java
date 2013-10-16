package deco2800.arcade.snakeLadderModel;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

//Class dice for generating random number and getting the number
public class Dice {
	
	private int num;
	protected Texture diceOne, diceTwo, diceThree, diceFour, diceFive, diceSix;
	private static final int XPOS = 750; //x position of the dice
	private static final int YPOS = 450; //y position of the dice
	
	/**
	 * Constructor for Dice object. Initiate the dice number to 0.
	 * Also assigning the textures of each dice numbers
	 */
	public Dice(){
		this.num = 0;
		this.diceOne = new Texture(Gdx.files.classpath("images/dice1.png"));
		this.diceTwo = new Texture(Gdx.files.classpath("images/dice2.png"));
		this.diceThree = new Texture(Gdx.files.classpath("images/dice3.png"));
		this.diceFour = new Texture(Gdx.files.classpath("images/dice4.png"));
		this.diceFive = new Texture(Gdx.files.classpath("images/dice5.png"));
		this.diceSix = new Texture(Gdx.files.classpath("images/dice6.png"));
	}
	
	/**
	 * Simulate rolling the dice action by generating random number between 1 to 6
	 * @return generated integer between 1 to 6
	 */
	public int rollDice(){
		Random randomGenerator = new Random();
		this.num = randomGenerator.nextInt(6)+1;
		return this.num;
	}
	
	/**
	 * Get this dice number. If return 0 then the player hasn't rolled the dice yet.
	 * @return dice number
	 */
	public int getDiceNumber(){
		return this.num;
	}
	
	/**
	 * Render the dice image based on the dice number. Initially the dice image should be empty.
	 * @param batch used the draw into the stage
	 * @param plaernum to indicate the which dice belongs to which player. Player numbering start at 0 for the
	 * default player and 1 for AI
	 */
	public void renderDice(SpriteBatch batch, int playernum){
		int x = XPOS + 100*(playernum);
		switch (this.num){
		case 1:
			batch.draw(this.diceOne,x,YPOS);
			break;
		case 2:
			batch.draw(this.diceTwo,x,YPOS);
			break;
		case 3:
			batch.draw(this.diceThree,x,YPOS);
			break;
		case 4:
			batch.draw(this.diceFour,x,YPOS);
			break;
		case 5:
			batch.draw(this.diceFive,x,YPOS);
			break;
		case 6:
			batch.draw(this.diceSix,x,YPOS);
			break;
		default:
			//batch.draw(this.diceOne,0,0);
			break;
		}
	}


}
