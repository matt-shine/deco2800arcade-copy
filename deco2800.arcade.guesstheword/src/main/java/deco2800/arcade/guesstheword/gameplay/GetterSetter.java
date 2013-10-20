package deco2800.arcade.guesstheword.gameplay;

import com.badlogic.gdx.graphics.Texture;


/**
 * Getter and Setter Class for Guess The Word
 * 
 * @author Xu Duangui
 */
public class GetterSetter {
	//--------------------------
	//PRIVATE VARIABLES
	//--------------------------
	/**
	 * Texture for get and set methods.
	 * */
	private Texture texture;
	/**
	 * Storing of level for get and set methods.
	 * */
	private String level ;
	/**
	 * Storing of category for get and set methods.
	 * */
	private String category;
	/**
	 * Storing of word in category for get and set methods.
	 * */
	private String categoryItem ;
	/**
	 * Storing of score for get and set methods.
	 * */
	private int score ;
	/**
	 * Storing of Answer Count for get and set methods.
	 * */
	private int answerCount;
	/**
	 * Storing of hint position for get and set methods.
	 * */
	private int hintPosition;
	
	/**
	 * Storing of hint for get and set methods.
	 * */
	private boolean hint;
	
	//Score
	
	/**
	 * Set the score. 
	 * @param score - score to be set
	 * */
	public void setScore(int score){
		this.score = score;
	}
	/**
	 * Return the score.
	 * @return the score.
	 * */
	public int getScore(){
		return score;
	}
	
	//Answer Count
	/**
	 * Set the Answer Count. 
	 * @param answerCount - the number of counts
	 * */
	public void setAnswerCount(int answerCount){
		this.answerCount = answerCount;
	}
	/**
	 * Return the Answer Count.
	 * @return the Answer Count.
	 * */
	public int getAnswerCount(){
		return answerCount;
	}
	//LEVEL
	/**
	 * Set the Level. 
	 * @param level - current level of the game
	 * */
	public void setLevel(String level){
		this.level = level;
	}
	/**
	 * Return the Level.
	 * @return the level.
	 * */
	public String getLevel(){
		return level;
	}
	
	//CATEGORY
	/**
	 * Set the Category. 
	 * @param category - category to be set
	 * */
	public void setCategory(String category){
		this.category = category;
	}
	/**
	 * Return the Category
	 * @return the category
	 * */
	public String getCategory(){
		return category;
	}
	
	/**
	 * Set the Word in the Category. 
	 * @param categoryItem - current word to be set.
	 * */
	public void setCategoryItem(String categoryItem){
		this.categoryItem = categoryItem;
	}
	
	/**
	 * Return the word in category
	 * @return the word in category.
	 * */
	public String getCategoryItem(){
		return categoryItem;
	}
	
	//Texture
	/**
	 * Set the texture for the game screen
	 * @param texture - texture to be paint
	 * */
	public void setTexture(Texture texture){
		this.texture = texture;
	}
	
	/**
	 * Return the Texture to be drawn
	 * @return the Texture.
	 * */
	public Texture getTexture(){
		return texture;
	}
	
	//Hint
	/**
	 * Set the status of hint taken
	 * @param hint - status of hint (True or false)
	 * */
	public void setHint(Boolean hint){
		this.hint = hint;
	}
	/**
	 * Return the Hint status.
	 * @return the Hint status.
	 * */
	public boolean getHint(){
		return hint;
	}
	
	/**
	 * Set the letter position in the word. 
	 * @param hintPosition - Position of letter from current word
	 * */
	public void setHintPosition(int hintPosition){
		this.hintPosition = hintPosition;
	}
	/**
	 * Return the Hint position.
	 * @return the Hint position.
	 * */
	public int getHintPosition(){
		return hintPosition;
	}
	

}
