package deco2800.arcade.userui;

import javax.swing.ImageIcon;

import deco2800.arcade.client.AchievementClient;
import deco2800.arcade.client.GameClient;
import deco2800.arcade.model.Friends;
import deco2800.arcade.model.Game;
import deco2800.arcade.model.Player;

public class Model {
	
	public Player player;
	
	//Game Logos
	public ImageIcon astrosonicLogo = new ImageIcon("assets/images/logos/Astrosonic.png");
	public ImageIcon chessLogo = new ImageIcon("assets/images/logos/chess.png");
	public ImageIcon defaultLogo = new ImageIcon("assets/images/logos/deafult.png");
	public ImageIcon breakoutLogo = new ImageIcon("assets/images/logos/breakout.png");
	public ImageIcon mixmazeLogo = new ImageIcon("assets/images/logos/mixmaze.png");
	public ImageIcon landinvaderslogo = new ImageIcon("assets/images/logos/landinvaders.png");
	public ImageIcon pacmanlogo = new ImageIcon("assets/images/logos/pacman.png");
	
	//List of Games
	public Game pong;
	public Game burningskies;
	public Game breakout;
	public Game pacman;
	
	public String status = "online";
	public ImageIcon statusIcon = new ImageIcon("assets/images/online.png");
	public Friends friends;
	public AchievementClient achievements;

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
