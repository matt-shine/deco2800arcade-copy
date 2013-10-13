package deco2800.arcade.guesstheword.gameplay;

import com.badlogic.gdx.graphics.Texture;

public class GetterSetter {
	
	private Texture texture;
	private String level ;
	private String category, categoryItem ;
	private String  text1 = "" ;
	private String  text2 = "" ;
	private String  text3 = "" ;
	private String  text4 = "" ;
	private String  text5 = "" ;
	private String  text6 = "" ;
	
	private String button1 = "";
	private String button2 = "";
	
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
	//TEXTFIELDS
	public void setText(String text){
		this.text1 = text;
	}
	public String getText(){
		return text1;
	}
	
	
	public void setText1(String text1){
		this.text1 = text1;
	}
	public String getText1(){
		return text1;
	}
	
	public void setText2(String text2){
		this.text2 = text2;
	}
	public String getText2(){
		return text2;
	}
	
	public void setText3(String text3){
		this.text3 = text3;
	}
	public String getText3(){
		return text3;
	}
	
	public void setText4(String text4){
		this.text4 = text4;
	}
	public String getText4(){
		return text4;
	}

	public void setText5(String text5){
		this.text5 = text5;
	}
	public String getText5(){
		return text5;
	}
	
	public void setText6(String text6){
		this.text6 = text6;
	}
	public String getText6(){
		return text6;
	}
	
	//TEXTBUTTONS
	public void setButton1(String button1){
		this.button1 = button1;
	}
	public String getButton1(){
		return button1;
	}
	public void setButton2(String button2){
		this.button2 = button2;
	}
	public String getButton2(){
		return button2;
	}
	


}
