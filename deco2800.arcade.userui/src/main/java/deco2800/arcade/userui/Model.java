package deco2800.arcade.userui;

import javax.swing.ImageIcon;

import deco2800.arcade.client.AchievementClient;
import deco2800.arcade.client.Arcade;
import deco2800.arcade.client.ArcadeSystem;
import deco2800.arcade.client.GameClient;
import deco2800.arcade.model.Friends;
import deco2800.arcade.model.Game;
import deco2800.arcade.model.Player;

public class Model {
		
	//Game Logos
	public ImageIcon astrosonicLogo = new ImageIcon("assets/images/logos/Astrosonic.png");
	public ImageIcon chessLogo = new ImageIcon("assets/images/logos/chess.png");
	public ImageIcon defaultLogo = new ImageIcon("assets/images/logos/deafult.png");
	public ImageIcon breakoutLogo = new ImageIcon("assets/images/logos/breakout.png");
	public ImageIcon mixmazeLogo = new ImageIcon("assets/images/logos/mixmaze.png");
	public ImageIcon landinvaderslogo = new ImageIcon("assets/images/logos/landinvaders.png");
	public ImageIcon pacmanlogo = new ImageIcon("assets/images/logos/pacman.png");
	
	//List of Games
	public static final Game PONG;
	public static final Game BURNINGSKIES;
	public static final Game BREAKOUT;
	public static final Game CHESS;
	public static final Game MIXMAZE;
	public static final Game LANDINVADERS;
	public static final Game PACMAN;
	
	//Status Values
	public String status = "online";
	public ImageIcon statusIcon = new ImageIcon("assets/images/online.png");
	
	public Friends friends;
	public AchievementClient achievements;
	public ArcadeSystem arcadesystem;
	public Player player;
	
	static {
		
		PONG = new Game();
		PONG.id = "pong";
		PONG.name = "Pong";
		PONG.description = "Tennis, without that annoying 3rd dimension!";
		
		BURNINGSKIES = new Game();
		BURNINGSKIES.id = "burningskies";
		BURNINGSKIES.name = "Burning Skies";
		BURNINGSKIES.description = "Engage in one of the most thrilling space combat battles this side of the galaxy in the critically acclaimed";
		
		BREAKOUT = new Game();
		BREAKOUT.id = "breakout";
		BREAKOUT.name = "Breakout";
		BREAKOUT.description = "Bounce the ball off your paddle to keep it from falling off the bottom of the screen.";
	
		CHESS = new Game();
		CHESS.id = "chess";
		CHESS.name = "Chess";
		CHESS.description = "The oldest and most classic board game...";
		
		MIXMAZE = new Game();
		MIXMAZE.id = "mixmaze";
		MIXMAZE.name = "Mixmaze";
		MIXMAZE.description = "See for yourself!";
		
		LANDINVADERS = new Game();
		LANDINVADERS.id = "landinvaders";
		LANDINVADERS.name = "Land Invaders";
		LANDINVADERS.description = "Space Invaders but better";
		
		PACMAN = new Game();
		PACMAN.id = "pacman";
		PACMAN.name = "Pac Man";
		PACMAN.description = "Eat the ghosts before they eat you!";
	
	}
	
	public String getStatus() {
		return status;	
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public ImageIcon getStatusIcon() {
		return statusIcon;
	}

	public void setStatusIcon(ImageIcon statusIcon) {
		this.statusIcon = statusIcon;
	}

	public AchievementClient getAchievements() {
		return achievements;
	}

	public void setAchievements(AchievementClient achievements) {
		this.achievements = achievements;
	}
	
}
