package deco2800.arcade.guesstheword.gameplay;

import deco2800.arcade.guesstheword.GUI.GuessTheWord;

public class Achievements {
	
	public enum AchievementType {
		level1 , level2, level3, animals, brands, countries , sports, transports
	}
	
	private final static IllegalArgumentException noAchievement 
						= new IllegalArgumentException("No such Achievement");

    private static Achievements achievement;
	
	private GuessTheWord game;
	
	private Achievements(GuessTheWord game){
		this.game = game;
	}
	
	public static Achievements createAchievementInstance(GuessTheWord game){
		achievement = new Achievements(game);
		return achievement;
	}
	
	public Achievements getAchivement(){
		return achievement;
	}
	
	private String getAchievementType(AchievementType type){
		switch(type){
			case level1 : return "guesstheword.level1";
			
			case level2 : return "guesstheword.level2";
			
			case level3 : return "guesstheword.level3";
			
			case animals : return "guesstheword.animals";
			
			case brands : return "guesstheword.brands";
			
			case countries : return "guesstheword.countries";
			
			case sports : return "guesstheword.sports";
			
			case transports : return "guesstheword.transports";
			
		}
		throw noAchievement;
	}
	
	public void incrementAchievement(AchievementType type) {
		String achievementID = getAchievementType(type);
		game.incrementAchievement(achievementID);
	}
}
