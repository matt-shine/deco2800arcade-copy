package deco2800.arcade.guesstheword.gameplay;

import com.badlogic.gdx.graphics.Texture;

public class GetterSetter {
	
	private Texture texture;
	private String level ;
	private String category, categoryItem ;
	private int score ;
	private int answerCount;
	private int hintPosition;
	private boolean hint;
	
	//Score
	public void setScore(int score){
		this.score = score;
	}
	public int getScore(){
		return score;
	}
	
	//Answer Count
	public void setAnswerCount(int answerCount){
		this.answerCount = answerCount;
	}
	public int getAnswerCount(){
		return answerCount;
	}
	//LEVEL
	public void setLevel(String level){
		this.level = level;
	}
	public String getLevel(){
		return level;
	}
	
	//CATEGORY
	public void setCategory(String category){
		this.category = category;
	}
	public String getCategory(){
		return category;
	}
	public void setCategoryItem(String categoryItem){
		this.categoryItem = categoryItem;
	}
	public String getCategoryItem(){
		return categoryItem;
	}
	
	//Texture
	public void setTexture(Texture texture){
		this.texture = texture;
	}
	public Texture getTexture(){
		return texture;
	}
	
	//Hint
	public void setHint(Boolean hint){
		this.hint = hint;
	}
	public boolean getHint(){
		return hint;
	}
	public void setHintPosition(int hintPosition){
		this.hintPosition = hintPosition;
	}
	public int getHintPosition(){
		return hintPosition;
	}
	

}
