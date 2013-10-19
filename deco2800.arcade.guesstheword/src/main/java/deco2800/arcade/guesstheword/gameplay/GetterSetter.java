package deco2800.arcade.guesstheword.gameplay;

import com.badlogic.gdx.graphics.Texture;

public class GetterSetter {
	
	private Texture texture;
	private String level ;
	private String category, categoryItem ;
	private int score ;
	
	//score
	public void setScore(int score){
		this.score = score;
	}
	public int getScore(){
		return score;
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
	
	public void setTexture(Texture texture){
		this.texture = texture;
	}
	public Texture getTexture(){
		return texture;
	}

	


}
