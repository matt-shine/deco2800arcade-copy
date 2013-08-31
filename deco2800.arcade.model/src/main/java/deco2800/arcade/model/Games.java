package deco2800.arcade.model;

import java.util.HashSet;
import java.util.Set;

public class Games {
	
	private Set<Game> games;
	
	public Games(){
		this.games = new HashSet<Game>();
	}
	
	public Games(Games g){
		this.games = new HashSet<Game>(g.games);
	}
	
	public Set<Game> getSet(){
		return new HashSet<Game>(games);
	}
	
	public void add(Game game){
		this.games.add(game);
	}
	
	public void remove(Game game){
		this.games.remove(game);
	}
	
	public boolean contains(Game game){
		return this.games.contains(game);
	}
	
}
